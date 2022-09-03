package pojo;

import pojo.InfoMessage;
import manage.PeerStorage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class InfoThread extends Thread{

    private Socket infoSocket;
    private String p;

    public InfoThread(Socket infoSocket) {
        this.infoSocket = infoSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("iii...");
                ObjectInputStream ois = new ObjectInputStream(infoSocket.getInputStream());
                // Make a thread distinction, so it's definitely Info that goes to Info
                InfoMessage ms = (InfoMessage) ois.readObject();
                // Forward
                if (ms.getDetailType().equals("2")){
                    System.out.println("Start forwarding" + p);
                    String content = ms.getContent();
                    System.out.println(infoSocket.getPort());
                    InfoThread i = PeerStorage.getI(content);
                    Socket socket = i.getInfoSocket();
                    System.out.println(socket.getPort());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(ms);
                    System.out.println("Complete forward");
                }
                // Forward
                else if(ms.getDetailType().equals("5")){
                    String[] target = ms.getContent().split("\\*");
                    new Thread(() -> {
                        InfoThread i = PeerStorage.getI(target[0]);
                        InfoMessage messageI1 = new InfoMessage();
                        messageI1.setType("1");
                        messageI1.setDetailType("5");
                        messageI1.setTaskName(ms.getTaskName());
                        try {
                            new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    new Thread(() -> {
                        InfoThread i = PeerStorage.getI(target[1]);
                        InfoMessage messageI1 = new InfoMessage();
                        messageI1.setType("1");
                        messageI1.setDetailType("5");
                        messageI1.setTaskName(ms.getTaskName());
                        try {
                            new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                else if(ms.getDetailType().equals("6")){
                    String[] target = ms.getContent().split("\\*");
                    new Thread(() -> {
                        InfoThread i = PeerStorage.getI(target[0]);
                        InfoMessage messageI1 = new InfoMessage();
                        messageI1.setType("1");
                        messageI1.setDetailType("3");
                        messageI1.setTaskName(ms.getTaskName());
                        try {
                            new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    new Thread(() -> {
                        InfoThread i = PeerStorage.getI(target[1]);
                        InfoMessage messageI1 = new InfoMessage();
                        messageI1.setType("1");
                        messageI1.setDetailType("3");
                        messageI1.setTaskName(ms.getTaskName());
                        try {
                            new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

            }catch (Exception e){
                System.out.println("endi" + p);
                PeerStorage.remove(p);
                try {
                    infoSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            }
            finally {
            }
        }
    }

    public Socket getInfoSocket() {
        return infoSocket;
    }

    public void setInfoSocket(Socket infoSocket) {
        this.infoSocket = infoSocket;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}

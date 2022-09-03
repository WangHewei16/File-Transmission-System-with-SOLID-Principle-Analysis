package pojo;

import manage.PeerStorage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class FileThread extends Thread{

    private Socket fileSocket;
    private boolean canUse;

    public FileThread(Socket fileSocket) {
        this.fileSocket = fileSocket;
        this.canUse = true;
    }

    public Socket getFileSocket() {
        return fileSocket;
    }

    public void setFileSocket(Socket fileSocket) {
        this.fileSocket = fileSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("fff...");
                ObjectInputStream ois = new ObjectInputStream(fileSocket.getInputStream());
                TransMessage message = (TransMessage) ois.readObject();
                String aim = message.getDetailType();
                String[] target = aim.split("\\*");
                // Straight forward
                System.out.println("开始转发");
                // All notices are downloaded by both parties
                // forward who
                new Thread(() -> {
                    InfoThread i = PeerStorage.getI(target[0]);
                    InfoMessage messageI1 = new InfoMessage();
                    messageI1.setType("1");
                    messageI1.setDetailType("3");
                    messageI1.setTaskName(message.getTaskName());
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
                    messageI1.setTaskName(message.getTaskName());
                    try {
                        new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                // Find the target pipeline
                FileThread f = PeerStorage.getF(target[0]);
                // If it's not available, it will close itself
                while (!f.isCanUse()){};
                // Take up pipeline
                f.setCanUse(false);
                ObjectOutputStream oos = new ObjectOutputStream(f.getFileSocket().getOutputStream());
                oos.writeObject(message);
                // Give the pipe a live line
                f.setCanUse(true);
                // All inform both parties of the end of transmission
                new Thread(() -> {
                    InfoThread i = PeerStorage.getI(target[0]);
                    InfoMessage messageI1 = new InfoMessage();
                    messageI1.setType("1");
                    messageI1.setDetailType("4");
                    messageI1.setTaskName(message.getTaskName());
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
                    messageI1.setDetailType("4");
                    messageI1.setTaskName(message.getTaskName());
                    try {
                        new ObjectOutputStream(i.getInfoSocket().getOutputStream()).writeObject(messageI1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }catch (Exception e){
                System.out.println("endf");
                try {
                    fileSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                break;
            }
            finally {
            }
        }
    }

    public boolean isCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }
}

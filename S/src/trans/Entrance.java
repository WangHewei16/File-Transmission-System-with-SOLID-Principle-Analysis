package trans;

import manage.PeerStorage;

import pojo.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Entrance {

    private ServerSocket s = null;

    public Entrance() {
        try {
            System.out.println("Server start");
            s = new ServerSocket(6666);
            while (true){
                Socket socket = s.accept();
                System.out.println("connect");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // Get stream
                InfoMessage ms = (InfoMessage) ois.readObject();
                // Verification
                if (ms.getType().equals("1") && ms.getDetailType().equals("1")){
                    Peer peer = new Peer(ms.getContent().split("-")[0], ms.getContent().split("-")[1]);
                    // Login successfully
                    InfoThread i = new InfoThread(socket);
                    i.setP(ms.getContent());
                    i.start();
                    FileThread f = new FileThread(s.accept());
                    f.start();
                    System.out.println("add " + ms.getContent());
                    PeerStorage.add(ms.getContent(), f, i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

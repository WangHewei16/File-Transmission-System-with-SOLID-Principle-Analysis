package pojo;

import manage.PeerStorage;

import java.io.*;
import java.net.Socket;

public class FileThread extends Thread{

    // file path
    private static String dir = System.getProperty("user.dir") + File.separatorChar;
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
                System.out.println("begin run...");
                ObjectInputStream ois = new ObjectInputStream(fileSocket.getInputStream());
                TransMessage message = (TransMessage) ois.readObject();
                // receive file
                FileOutputStream fileOutputStream = null;
                try {
                    File file = new File(dir +  message.getTaskName());
                    if (!file.exists() || !file.isDirectory()) {
                        file.mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(dir + message.getTaskName()
                            + File.separatorChar + message.getFileName());
                    fileOutputStream.write(message.getFileBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Receive files: " + message.getFileName());
            }catch (Exception e){
                try {
                    fileSocket.close();
                    System.out.println("file socket close");
                    break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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

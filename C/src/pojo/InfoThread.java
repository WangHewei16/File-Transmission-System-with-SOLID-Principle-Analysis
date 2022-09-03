package pojo;

import manage.TaskStorage;
import trans.FileTransImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class InfoThread extends Thread{

    private Socket infoSocket;

    public InfoThread(Socket infoSocket) {
        this.infoSocket = infoSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("begin run...");
                ObjectInputStream ois = new ObjectInputStream(infoSocket.getInputStream());
                InfoMessage ms = (InfoMessage) ois.readObject();
                if (ms.getDetailType().equals("3")){
                    // update status
                    Task task = TaskStorage.getTask().get(ms.getTaskName());
                    task.setStatus("1");
                    TaskStorage.change(task);
                    // recovery operation
                    FileTransImpl.changeSTW(false);
                }
                else if (ms.getDetailType().equals("4")){
                    // update status
                    Task task = TaskStorage.getTask().get(ms.getTaskName());
                    task.setStatus("2");
                    TaskStorage.change(task);
                }
                else if (ms.getDetailType().equals("5")){
                    // update status
                    Task task = TaskStorage.getTask().get(ms.getTaskName());
                    task.setStatus("3");
                    HashMap<String, String> files = task.getFiles();
                    Set<Map.Entry<String, String>> entries = files.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        if (!entry.getKey().equals("2")){
                            files.put(entry.getKey(), "3");
                        }
                    }
                    task.setFiles(files);
                    TaskStorage.change(task);
                    // stuck operating
                    FileTransImpl.changeSTW(true);
                }
                else{
                    System.out.println("receive updates");
                    Task task = new Task();
                    task.setStatus(ms.getStatus());
                    task.setFiles(ms.getFiles());
                    task.setTaskName(ms.getTaskName());
                    TaskStorage.change(task);
                    System.out.println("update completed");
                }
            }catch (Exception e){
                try {
                    infoSocket.close();
                    System.out.println("info socket close");
                    break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public Socket getInfoSocket() {
        return infoSocket;
    }

    public void setInfoSocket(Socket infoSocket) {
        this.infoSocket = infoSocket;
    }

}

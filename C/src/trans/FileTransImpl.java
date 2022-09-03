package trans;

import handler.FileUtils;
import manage.ConnectStorage;
import manage.FileStorage;
import manage.PeerStorage;
import manage.TaskStorage;
import pojo.FileThread;
import pojo.Task;
import pojo.TransMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;


public class FileTransImpl implements FileTrans {

    public static boolean STWFlag = false;

    @Override
    public void sendTask(String taskName){
        Task task = TaskStorage.getTask().get(taskName);
        List<String> files = FileStorage.get();
        System.out.println("start sending file");
        for (String file : files) {
            while (STWFlag){}
            System.out.println(files);
            TransMessage message = new TransMessage();
            message.setType("2");
            message.setTaskName(taskName);
            // target *split
            String aim = task.getAim() + "*" + PeerStorage.getInfo();
            message.setDetailType(aim);
            message.setFileName(FileUtils.getFileName(file));
            FileInputStream fileInputStream = null;
            byte[] fileBytes = new byte[65536];
            try {
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(fileBytes);
                // Set the file's byte array to message
                message.setFileBytes(fileBytes);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fileInputStream != null){
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // send
            try {
                FileThread fileThread = ConnectStorage.getFile();
                // If it's not available, it will close itself
                while (!fileThread.isCanUse()){};
                fileThread.setCanUse(false);
                ObjectOutputStream oos = new ObjectOutputStream(fileThread.getFileSocket().getOutputStream());
                oos.writeObject(message);
                // Available again
                fileThread.setCanUse(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("sending file...");
            Task taskT = TaskStorage.getTask().get(taskName);
            taskT.getFiles().put(FileUtils.getFileName(file), "2");
        }
        System.out.println("finish sending");
    }

    public static void changeSTW(boolean flag){
        STWFlag = flag;
    }
}

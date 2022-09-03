package trans;

import manage.ConnectStorage;
import pojo.InfoMessage;
import pojo.InfoThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;


public class InfoTransImpl implements InfoTrans {

    @Override
    public void sendInfo(String type, String detailType, String taskName, HashMap<String, String> files, String content){
        InfoMessage message = new InfoMessage();
        message.setDetailType(detailType);
        message.setType(type);
        message.setTaskName(taskName);
        message.setFiles(files);
        message.setContent(content);
        InfoThread thread = ConnectStorage.getInfo();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(thread.getInfoSocket().getOutputStream());
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

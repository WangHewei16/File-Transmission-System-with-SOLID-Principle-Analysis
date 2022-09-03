package pojo;

import pojo.BaseMessage;

import java.util.HashMap;
import java.util.List;

// 1 connect
// 2 start task
// 3 downloading
// 4 finish
// 5 pause
// 6 restart

public class InfoMessage extends BaseMessage {

    private String content;
    private String taskName;
    private HashMap<String, String> files;
    private String status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public void setFiles(HashMap<String, String> files) {
        this.files = files;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

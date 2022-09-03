package pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

// 0 request
// 1 25%
// 2 50%
// 3 75%
// 4 100%
// 5 pause

public class Task {

    private String taskName;
    private HashMap<String, String> files;
    private String status;

    private String aim;

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
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

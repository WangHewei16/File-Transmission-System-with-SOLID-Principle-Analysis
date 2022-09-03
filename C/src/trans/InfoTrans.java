package trans;

import java.util.HashMap;

public interface InfoTrans {
    void sendInfo(String type, String detailType, String taskName, HashMap<String, String> files, String content);
}

package pojo;

import java.io.Serializable;

public class BaseMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    // 1 operation
    // 2 file

    private String type;
    private String detailType;

    public BaseMessage(String type, String detailType) {
        this.type = type;
        this.detailType = detailType;
    }

    public BaseMessage() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }
}

package pojo;

import java.io.Serializable;


public class Peer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String port;
    private String ip;

    public Peer(String port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public String toMs(){
        return ip + "-" + port;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

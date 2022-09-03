package trans;

import manage.ConnectStorage;
import manage.PeerStorage;
import pojo.FileThread;
import pojo.InfoMessage;
import pojo.InfoThread;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class ConnectTransImpl implements ConnectTrans {

    private static Socket socket;

    @Override
    public void connect(String ip, String port) {
        try {
            socket = new Socket("127.0.0.1", 6666);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 发送msg
            InfoMessage message = new InfoMessage();
            message.setContent(ip + "-" + port);
            message.setType("1");
            message.setDetailType("1");
            oos.writeObject(message);
            // init
            ConnectStorage.init(new FileThread(new Socket(InetAddress.getByName("127.0.0.1"), 6666)),new InfoThread(socket));
            ConnectStorage.getFile().start();
            ConnectStorage.getInfo().start();
            PeerStorage.setInfo(ip + "-" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package manage;


public class PeerStorage extends BaseStorage{

    private static String peerInfo = null;

    public static void setInfo(String peer){
        peerInfo = peer;
    }

    public static String getInfo(){
        getStorage();
        return peerInfo;
    }

    public static void remove(){
        clearStorage();
        peerInfo = null;
    }
}

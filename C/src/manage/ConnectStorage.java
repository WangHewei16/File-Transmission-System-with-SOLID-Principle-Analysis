package manage;

import pojo.FileThread;
import pojo.InfoThread;


public class ConnectStorage extends BaseStorage{

    private static FileThread fileThread;
    private static InfoThread infoThread;

    public static void init(FileThread f, InfoThread i){
        fileThread = f;
        infoThread = i;
    }

    public static FileThread getFile(){
        getStorage();
        return fileThread;
    }

    public static InfoThread getInfo(){
        getStorage();
        return infoThread;
    }

    public static void clear(){
        clearStorage();
        fileThread = null;
        infoThread = null;
    }
}

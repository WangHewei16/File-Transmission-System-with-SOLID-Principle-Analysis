package manage;

import pojo.*;

import java.util.HashMap;
import java.util.Map;


public class PeerStorage {

    private static Map<String, FileThread> fList = new HashMap<>();
    private static Map<String, InfoThread> iList = new HashMap<>();

    public static void add(String peer, FileThread f, InfoThread i){
        fList.put(peer, f);
        iList.put(peer, i);
    }

    public static boolean isExist(String peer){
        return fList.containsKey(peer);
    }
    public static FileThread getF(String s){
        return fList.get(s);
    }
    public static InfoThread getI(String s){
        System.out.println(iList.size());
        System.out.println("getting I");
        System.out.println(s);
        System.out.println("s");
        return iList.get(s);
    }

    public static void remove(String peer){
        fList.remove(peer);
        iList.remove(peer);
    }
}

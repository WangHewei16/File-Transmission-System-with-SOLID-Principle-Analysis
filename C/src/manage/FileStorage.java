package manage;

import java.util.ArrayList;
import java.util.List;


public class FileStorage extends BaseStorage{

    private static List<String> list = new ArrayList<>();

    public static void add(String s) {
        append();
        list.add(s);
    }

    public static List<String> get(){
        getStorage();
        return list;
    }

    public static void clear(){
        clearStorage();
        list.clear();
    }
}

package handler;

import java.io.File;

public class FileUtils {

    public static String getFileName(String path){
        File file = new File(path);
        if (file.exists() && file.isFile()){
            String name = file.getName();
            System.out.println(name);
            return name;
        }
        return "error";
    }

}

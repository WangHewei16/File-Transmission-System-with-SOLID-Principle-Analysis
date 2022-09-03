package manage;

import pojo.Task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TaskStorage extends BaseStorage{

    // Thread-safe concurrency processing

    private static Map<String, Task> taskMap = new ConcurrentHashMap<>();

    public static void change(Task t){
        changeStorage();
        taskMap.put(t.getTaskName(), t);
    }

    public static Map<String, Task> getTask(){
        getStorage();
        return taskMap;
    }
}



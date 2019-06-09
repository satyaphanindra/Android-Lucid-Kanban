package com.citta.lucidkanban.managers;

import android.content.Context;

import com.citta.lucidkanban.LucidApplication;
import com.citta.lucidkanban.data.Storage;
import com.citta.lucidkanban.model.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskManager {

    public List<Task> tasksList = new ArrayList<Task>();

    private static TaskManager uniqInstance;

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (uniqInstance == null) {
            synchronized (TaskManager.class) {
                // check again to avoid multi-thread access
                if (uniqInstance == null)
                    uniqInstance = new TaskManager();
            }
        }
        return uniqInstance;
    }
    // other useful methods here

    public void addTaskItem(Task item) {
        tasksList.add(item);
    }

    public void replaceExistingTaskItem(String id, Task newTask) {
        int i = 0;
        for(Task task : tasksList) {
            if(task.taskId.equals(id)) {
                //found it!
                tasksList.set(i, newTask);
                break;
            }
            i++;
        }
    }

    public Task getTaskWithId(String id) {
        Task task = null;
        int i = 0;
        for(Task taskItem : tasksList) {
            if(taskItem.taskId.equals(id)) {
                //found it!
                task = tasksList.get(i);
                break;
            }
            i++;
        }
        return task;
    }

    public void removeTaskItem(Task item) {
        tasksList.remove(item);
    }

    public void removeTaskAtPosition(int position) {
        tasksList.remove(position);
    }

    public void removeTaskWithTaskId(int position) {
        tasksList.remove(position);
    }

    public void removeAllTasks() {
        tasksList.clear();
    }

    public void saveTask(Context context) {
        Storage storage = new Storage(context);
        storage.store(tasksList, "TasksFile", Storage.Directory.Documents);
    }

    public void initialize() {
        Storage storage = new Storage(LucidApplication.getInstance());
        List<Task> itemList = (List<Task>) storage.retrieve("File", Storage.Directory.Documents);
        tasksList = itemList;
    }
}

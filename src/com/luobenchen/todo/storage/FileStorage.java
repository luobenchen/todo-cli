package com.luobenchen.todo.storage;
//将来扩展或替换存储方式（比如换成数据库）时，只需要修改FileStorage，而不影响业务逻辑
import com.luobenchen.todo.model.Task;
import com.luobenchen.todo.service.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage implements Storage {
    private final String filename;
    //有参构造函数
    public FileStorage(String filename) {
        this.filename = filename;
    }

    @Override
    @SuppressWarnings("unchecked")//警告忽略
    public List<Task> loadTasks() {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        //实现从文件加载任务列表

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // 在实际应用中应该使用日志记录
            System.err.println("读取任务文件失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.err.println("保存任务失败: " + e.getMessage());
        }
    }
}
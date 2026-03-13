package com.luobenchen.todo.service;

import com.luobenchen.todo.model.Task;
import java.util.List;
//接口定义了解耦的契约。将来如果改用数据库，只需新建DatabaseStorage实现该接口即可
public interface Storage {
    List<Task> loadTasks();      // 加载所有任务
    void saveTasks(List<Task> tasks); // 保存所有任务
}
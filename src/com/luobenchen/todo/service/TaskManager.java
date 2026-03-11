package com.luobenchen.todo.service;



import com.luobenchen.todo.model.Task;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskManager {
    private List<Task> tasks;
    private final Storage storage;
    private final AtomicInteger nextId; // 线程安全的ID生成器

    public TaskManager(Storage storage) {
        this.storage = storage;
        this.tasks = storage.loadTasks();
        //这段代码用于初始化下一个任务的 ID：
        //找出最大 ID：遍历所有任务，获取最大的任务 ID（若没有任务则为 0）
        //设置下一个 ID：将 nextId 初始化为最大 ID 加 1，确保新任务 ID 不会重复
        int maxId = tasks.stream().mapToInt(Task::getId).max().orElse(0);
        this.nextId = new AtomicInteger(maxId + 1);
    }

    // 添加任务
    public Task addTask(String title, String description) {
        //AtomicInteger 的 getAndIncrement() 方法会先返回当前的 nextId 值，然后将其加 1。
        // 这确保了每次获取的 ID 都是唯一且递增的，即使在多线程环境下也是线程安全的。
        Task task = new Task(nextId.getAndIncrement(), title, description);
        tasks.add(task);
        save();
        return task;
    }

    // 列出所有任务
    public List<Task> getAllTasks() {
        return tasks;
    }

    // 标记任务为已完成
    public boolean markCompleted(int id) {
        Task task = findTaskById(id);
        if (task != null && !task.isCompleted()) {
            task.setCompleted(true);
            save();
            return true;
        }
        return false;
    }

    // 删除任务
    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) {
            save();
        }
        return removed;
    }

    private Task findTaskById(int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    private void save() {
        storage.saveTasks(tasks);
    }
}
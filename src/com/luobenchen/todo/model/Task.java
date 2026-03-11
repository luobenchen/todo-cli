package com.luobenchen.todo.model;
//创建应用model
import java.io.Serial;
import java.io.Serializable;//便于将对象写入文件
import java.time.LocalDateTime;

public class Task implements Serializable {
    /*public static void main(String[] args) {
        Task task =new Task(1,"实验1","准备完成实验一");
        System.out.println(task.toString());
//        输出结果如下
//        [1] [×] 实验1 (创建于 2026-03-10 17:38:11)
//        描述: 准备完成实验一
//
//        进程已结束，退出代码为 0
    }*/
    @Serial//：明确该字段是用于序列化版本控制的
    private static final long serialVersionUID = 1L;
    private int id;//任务编号
    private String title;//任务标题
    private String description;//任务详细描述
    private boolean completed;//判断任务是否完成
    private LocalDateTime createdAt;//任务创建时间

    // 有参构造函数
    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // getters 和 setters
    // 是否可能将 Task 对象序列化为 JSON（如通过 REST API 返回）？这会需要 getter。
    //是否可能通过 UI 修改任务标题？这需要 setter。
    public int getId() { return id; }
    //public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    //public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    // 重写toString方法
    public String toString() {
        return String.format("[%d] %s %s (创建于 %s)%s",
                id,
                completed ? "[✓]" : "[×]",
                title,
                createdAt.toString().substring(0, 19).replace('T', ' '),//截取时间，并使输出更加美观
                description != null && !description.isEmpty() ? "\n    描述: " + description : "");//检查描述字段既不是 null，也不是空字符串
    }
}
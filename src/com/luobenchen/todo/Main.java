package com.luobenchen.todo;

import com.luobenchen.todo.model.Task;
import com.luobenchen.todo.service.TaskManager;
import com.luobenchen.todo.storage.FileStorage;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "tasks.dat";

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager(new FileStorage(DATA_FILE));
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用TODO管理器！");
        printHelp();

        while (true) {
            System.out.print("\n请输入命令: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;
            // 按空格分割，最多两部分,\s+ 表示匹配一个或多个空白字符,2 表示限制分割结果为最多 2 个部分
            String[] parts = line.split("\\s+", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1] : "";

            switch (command) {
                case "add":
                    handleAdd(taskManager, argument, scanner);
                    break;
                case "list", "ls":
                    handleList(taskManager);
                    break;
                case "done":
                    handleDone(taskManager, argument);
                    break;
                case "delete":
                    handleDelete(taskManager, argument);
                    break;
                case "help":
                    printHelp();
                    break;
                case "exit":
                    System.out.println("再见！");
                    scanner.close();
                    return;
                default:
                    System.out.println("未知命令，输入 help 查看帮助");
            }
        }
    }

    private static void handleAdd(TaskManager taskManager, String argument, Scanner scanner) {
        String title, description = "";
        if (argument.isEmpty()) {
            System.out.print("请输入任务标题: ");
            title = scanner.nextLine();
            System.out.print("请输入任务描述（可选，直接回车跳过）: ");
            description = scanner.nextLine();
        } else {
            // 支持直接输入：add 标题
            title = argument;
        }
        Task task = taskManager.addTask(title, description.isEmpty() ? null : description);
        System.out.println("任务已添加，ID = " + task.getId());
    }

    private static void handleList(TaskManager taskManager) {
        List<Task> tasks = taskManager.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("暂无任务，使用 add 添加吧！");
        } else {
            System.out.println("当前任务列表：");
            tasks.forEach(System.out::println);
        }
    }

    private static void handleDone(TaskManager taskManager, String argument) {
        try {
            int id = Integer.parseInt(argument.trim());
            boolean success = taskManager.markCompleted(id);
            if (success) {
                System.out.println("任务 " + id +  " 已完成！");
            } else {
                System.out.println("任务不存在或已完成，请检查ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的任务ID，如：done 1");
        }
    }

    private static void handleDelete(TaskManager taskManager, String argument) {
        try {
            int id = Integer.parseInt(argument.trim());
            boolean success = taskManager.deleteTask(id);
            if (success) {
                System.out.println("任务 " + id + " 已删除！");
            } else {
                System.out.println("任务不存在，请检查ID");
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的任务ID，如：delete 1");
        }
    }

    private static void printHelp() {
        System.out.println("\n可用命令：");
        System.out.println("  add [标题]          - 添加新任务，如果不带标题则交互输入");
        System.out.println("  list/ls            - 显示所有任务");
        System.out.println("  done <任务ID>       - 标记任务为已完成");
        System.out.println("  delete <任务ID>     - 删除任务");
        System.out.println("  help                - 显示此帮助");
        System.out.println("  exit                - 退出程序");
    }
}
/*
运行结果展示：
欢迎使用TODO管理器！

可用命令：
  add [标题]          - 添加新任务，如果不带标题则交互输入
  list/ls            - 显示所有任务
  done <任务ID>       - 标记任务为已完成
  delete <任务ID>     - 删除任务
  help                - 显示此帮助
  exit                - 退出程序

请输入命令: add test
任务已添加，ID = 3

请输入命令: done 1
任务不存在或已完成，请检查ID

请输入命令: done 3
任务 3 已完成！

请输入命令: ls
当前任务列表：
[1] [✓] link (创建于 2026-03-11 21:36:06)
[2] [✓] seek (创建于 2026-03-11 21:36:50)
[3] [✓] test (创建于 2026-03-11 21:38:34)

请输入命令: delete 1
任务 1 已删除！

请输入命令: ls
当前任务列表：
[2] [✓] seek (创建于 2026-03-11 21:36:50)
[3] [✓] test (创建于 2026-03-11 21:38:34)

请输入命令: add copy
任务已添加，ID = 4

请输入命令: ls
当前任务列表：
[2] [✓] seek (创建于 2026-03-11 21:36:50)
[3] [✓] test (创建于 2026-03-11 21:38:34)
[4] [×] copy (创建于 2026-03-11 21:39:27)

请输入命令: exit
再见！

进程已结束，退出代码为 0*/
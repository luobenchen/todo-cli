构建一个能运行在终端/命令行的TODO管理器，具备以下功能：

- 添加任务（带标题和可选的描述）
- 查看所有任务（列出ID、标题、完成状态）
- 标记任务为已完成
- 删除任务
- 数据持久化（关闭程序后再次打开，任务依然存在）

核心技术

- Java基础（OOP、集合框架`ArrayList`）
- 文件IO（`FileWriter`/`BufferedReader`进行数据持久化）
- 异常处理

最终展示

```
PS C:\LJS\java\todo-cli\out\artifacts\todo_cli_jar> java -jar todo-cli.jar
欢迎使用TODO管理器！

可用命令：
  add [标题]          - 添加新任务，如果不带标题则交互输入
  list/ls            - 显示所有任务
  done <任务ID>       - 标记任务为已完成
  delete <任务ID>     - 删除任务
  help                - 显示此帮助
  exit                - 退出程序

请输入命令: add jar包测试
任务已添加，ID = 1

请输入命令: list
当前任务列表：
[1] [×] jar包测试 (创建于 2026-03-12 17:51:50)

请输入命令: done 1
任务 1 已完成！

请输入命令: add test over
任务已添加，ID = 2

请输入命令: done 2
任务 2 已完成！

请输入命令: ls
当前任务列表：
[1] [?] jar包测试 (创建于 2026-03-12 17:51:50)
[2] [?] test over (创建于 2026-03-12 17:52:14)

请输入命令: exit
再见！
PS C:\LJS\java\todo-cli\out\artifacts\todo_cli_jar> 

```

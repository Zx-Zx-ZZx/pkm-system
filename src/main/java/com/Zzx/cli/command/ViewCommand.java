package com.Zzx.cli.command;


public class ViewCommand extends AbstractCommand {
    public ViewCommand() {
        super("view", "查看指定笔记内容");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String noteId = args[0];
        System.out.println("查看笔记 ID: " + noteId);
        System.out.println("标题: Java笔记");
        System.out.println("内容: 这是关于Java编程的笔记内容...");
        System.out.println("标签: [编程, 学习]");
        System.out.println("创建时间: 2023-10-01");
        // TODO: 调用 NoteController.getNote()
    }

    @Override
    public void printUsage() {
        System.out.println("用法: view <笔记ID>");
        System.out.println("示例: view 1");
    }
}
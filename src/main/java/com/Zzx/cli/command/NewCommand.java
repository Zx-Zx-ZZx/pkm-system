package com.Zzx.cli.command;


public class NewCommand extends AbstractCommand {
    public NewCommand() {
        super("new", "创建新笔记");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String title = args[0].replaceAll("^\"|\"$", "");
        String content = args[1].replaceAll("^\"|\"$", "");

        System.out.println("创建笔记: " + title);
        System.out.println("内容: " + content);
        // TODO: 调用 NoteController.createNote()
    }

    @Override
    public void printUsage() {
        System.out.println("用法: new <标题> <内容>");
        System.out.println("示例: new \"Java笔记\" \"面向对象编程的三大特性...\"");
    }
}

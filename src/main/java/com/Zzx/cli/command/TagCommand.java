package com.Zzx.cli.command;



public class TagCommand extends AbstractCommand {
    public TagCommand() {
        super("tag", "为笔记添加标签");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String noteId = args[0];
        String tag = args[1];

        System.out.println("为笔记 " + noteId + " 添加标签: " + tag);
        // TODO: 调用 NoteController.addTag()
        System.out.println("标签添加成功");
    }

    @Override
    public void printUsage() {
        System.out.println("用法: tag <笔记ID> <标签名>");
        System.out.println("示例: tag 1 java");
    }
}

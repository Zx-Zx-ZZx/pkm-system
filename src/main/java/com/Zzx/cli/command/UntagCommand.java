package com.Zzx.cli.command;



public class UntagCommand extends AbstractCommand {
    public UntagCommand() {
        super("untag", "移除笔记的标签");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String noteId = args[0];
        String tag = args[1];

        System.out.println("从笔记 " + noteId + " 移除标签: " + tag);
        // TODO: 调用 NoteController.removeTag()
        System.out.println("标签移除成功");
    }

    @Override
    public void printUsage() {
        System.out.println("用法: untag <笔记ID> <标签名>");
        System.out.println("示例: untag 1 java");
    }
}

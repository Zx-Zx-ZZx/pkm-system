package com.Zzx.cli.command;



public class DeleteCommand extends AbstractCommand {
    public DeleteCommand() {
        super("delete", "删除指定笔记");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String noteId = args[0];
        System.out.println("删除笔记 ID: " + noteId);
        // TODO: 调用 NoteController.deleteNote()
        System.out.println("笔记已删除");
    }

    @Override
    public void printUsage() {
        System.out.println("用法: delete <笔记ID>");
        System.out.println("示例: delete 1");
    }
}
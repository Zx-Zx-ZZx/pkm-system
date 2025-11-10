package com.Zzx.cli.command;


public class EditCommand extends AbstractCommand {
    public EditCommand() {
        super("edit", "编辑指定笔记");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            printUsage();
            return;
        }

        String noteId = args[0];
        String title = args[1].replaceAll("^\"|\"$", "");
        String content = args[2].replaceAll("^\"|\"$", "");

        System.out.println("编辑笔记 ID: " + noteId);
        System.out.println("新标题: " + title);
        System.out.println("新内容: " + content);
        // TODO: 调用 NoteController.updateNote()
    }

    @Override
    public void printUsage() {
        System.out.println("用法: edit <笔记ID> <新标题> <新内容>");
        System.out.println("示例: edit 1 \"新标题\" \"新内容...\"");
    }
}

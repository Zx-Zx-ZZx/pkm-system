package com.Zzx.cli.command;



public class ExportAllCommand extends AbstractCommand {
    public ExportAllCommand() {
        super("export-all", "导出所有笔记");
    }

    @Override
    public void execute(String[] args) {
        String format = "markdown"; // 默认格式
        if (args.length > 0) {
            format = args[0];
        }

        System.out.println("导出所有笔记，格式: " + format);
        // TODO: 调用 NoteController.exportAllNotes()
        System.out.println("所有笔记已导出到: notes_export." + format);
    }

    @Override
    public void printUsage() {
        System.out.println("用法: export-all [格式]");
        System.out.println("示例: export-all");
        System.out.println("示例: export-all markdown");
        System.out.println("示例: export-all html");
    }
}
package com.Zzx.cli.command;


public class ExportCommand extends AbstractCommand {
    public ExportCommand() {
        super("export", "导出指定笔记");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String noteId = args[0];
        String format = "markdown"; // 默认格式
        if (args.length > 1) {
            format = args[1];
        }

        System.out.println("导出笔记 ID: " + noteId + " 格式: " + format);
        // TODO: 调用 NoteController.exportNote()
        System.out.println("笔记已导出到: note_" + noteId + "." + format);
    }

    @Override
    public void printUsage() {
        System.out.println("用法: export <笔记ID> [格式]");
        System.out.println("示例: export 1");
        System.out.println("示例: export 1 markdown");
        System.out.println("示例: export 1 html");
    }
}
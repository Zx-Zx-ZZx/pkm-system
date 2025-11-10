package com.Zzx.cli.command;



public class SearchCommand extends AbstractCommand {
    public SearchCommand() {
        super("search", "搜索笔记");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String keyword = String.join(" ", args);
        System.out.println("搜索关键词: " + keyword);
        // TODO: 调用 NoteController.searchNotes()
        System.out.println("搜索结果:");
        System.out.println("[1] Java笔记 - 包含 '" + keyword + "' 的内容");
        System.out.println("[3] 设计模式 - 包含 '" + keyword + "' 的内容");
    }

    @Override
    public void printUsage() {
        System.out.println("用法: search <关键词>");
        System.out.println("示例: search Java");
        System.out.println("示例: search \"设计模式\"");
    }
}
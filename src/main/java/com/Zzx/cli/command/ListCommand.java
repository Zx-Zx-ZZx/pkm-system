package com.Zzx.cli.command;


import java.util.HashMap;
import java.util.Map;

public class ListCommand extends AbstractCommand {
    public ListCommand() {
        super("list", "列出所有笔记");
    }

    @Override
    public void execute(String[] args) {
        Map<String, String> options = parseOptions(args);

        if (options.containsKey("tag")) {
            String tag = options.get("tag");
            System.out.println("列出标签为 '" + tag + "' 的笔记!");
        } else {
            System.out.println("列出所有笔记!");
        }
        // TODO: 调用 NoteController.listNotes()
        System.out.println("[1] Java笔记 (2023-10-01) [编程，学习]");
        System.out.println("[2] 设计模式笔记 (2023-10-05) [编程，架构]");
    }

    @Override
    public void printUsage() {
        System.out.println("用法: list [--tag TAG]");
        System.out.println("示例: list");
        System.out.println("示例: list --tag java");
    }

    private Map<String, String> parseOptions(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("--")) {
                String key = args[i].substring(2);
                if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    options.put(key, args[i + 1]);
                    i++;
                } else {
                    options.put(key, "true");
                }
            }
        }
        return options;
    }
}
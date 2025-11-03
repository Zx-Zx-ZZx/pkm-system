package com.Zzx.cli;

import com.Zzx.controller.NoteController;
import com.Zzx.controller.TagController;
import com.Zzx.service.NoteService;
import com.Zzx.service.TagService;
import com.Zzx.storage.JsonStorageService;
import com.Zzx.storage.StorageService;

import java.util.Scanner;

public class CommandParser {
    private final NoteController noteController;
    private final TagController tagController;
    private final Scanner scanner;
    private boolean isRunning;

    // 保持CommandParser不变
    public CommandParser() {
        // 依赖注入装配
        StorageService storageService = new JsonStorageService();
        NoteService noteService = new NoteService(storageService);
        TagService tagService = new TagService(storageService);
    
        this.noteController = new NoteController(noteService);  // 只传入noteService
        this.tagController = new TagController(tagService);
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    public void parseArgs(String[] args) {
        if (args.length == 0) {
            startInteractiveMode();
        } else {
            executeCommand(String.join(" ", args));
        }
    }

    private void startInteractiveMode() {
        System.out.println("欢迎使用个人知识管理系统（CLI版）");
        System.out.println("输入 help 查看可用命令\n");

        while (isRunning) {
            System.out.print("pkm> ");
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                executeCommand(input);
            }
        }
    }

    private void executeCommand(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length == 0) return;

        String command = parts[0].toLowerCase();
        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        try {
            switch (command) {
                case "new":
                    handleNewCommand(args);
                    break;
                case "list":
                    handleListCommand(args);
                    break;
                case "view":
                    handleViewCommand(args);
                    break;
                case "edit":
                    handleEditCommand(args);
                    break;
                case "delete":
                    handleDeleteCommand(args);
                    break;
                case "delete-tag":
                    handleDeleteTagCommand(args);
                    break;
                case "tag":
                    handleTagCommand(args);
                    break;
                case "untag":
                    handleUntagCommand(args);
                    break;
                case "search":
                    handleSearchCommand(args);
                    break;
                case "tags":
                    handleTagsCommand(args);
                    break;
                case "export":
                    handleExportCommand(args);
                    break;
                case "export-all":
                    handleExportAllCommand(args);
                    break;
                case "help":
                    handleHelpCommand();
                    break;
                case "exit":
                    handleExitCommand();
                    break;
                default:
                    System.err.println("未知命令: " + command);
                    System.out.println("输入 help 查看可用命令");
            }
        } catch (Exception e) {
            // 修复：当getMessage()返回null时显示异常类名
            String errorMessage = e.getMessage();
            if (errorMessage == null || errorMessage.trim().isEmpty()) {
                errorMessage = e.getClass().getSimpleName();
            }
            System.err.println("命令执行出错: " + errorMessage);
            // 可选：打印堆栈跟踪用于调试
            // e.printStackTrace();
        }
    }

    private void handleNewCommand(String[] args) {
        if (args.length < 1) {
            System.err.println("用法: new <标题> [内容]");
            return;
        }

        String title = args[0];
        String content = args.length > 1 ? args[1] : "";
        noteController.createNote(title, content);
    }

    private void handleListCommand(String[] args) {
        if (args.length > 0 && args[0].equals("--tag")) {
            if (args.length < 2) {
                System.err.println("用法: list --tag <标签名>");
                return;
            }
            noteController.listNotesByTag(args[1]);
        } else {
            noteController.listAllNotes();
        }
    }

    private void handleViewCommand(String[] args) {
        if (args.length < 1) {
            System.err.println("用法: view <笔记ID>");
            return;
        }
        noteController.viewNote(args[0]);
    }

    private void handleEditCommand(String[] args) {
        if (args.length < 2) {
            System.err.println("用法: edit <笔记ID> <新内容>");
            return;
        }
        noteController.editNote(args[0], args[1]);
    }

    private void handleDeleteTagCommand(String[] args) {
        if (args.length < 1) {
            System.err.println("用法: delete-tag <标签名>");
            return;
        }
        tagController.deleteTag(args[0]);
    }

    private void handleTagCommand(String[] args) {
        if (args.length < 2) {
            System.err.println("用法: tag <笔记ID> <标签>");
            return;
        }
        tagController.addTag(args[0], args[1]);
    }

    private void handleUntagCommand(String[] args) {
        if (args.length < 2) {
            System.err.println("用法: untag <笔记ID> <标签>");
            return;
        }
        tagController.removeTag(args[0], args[1]);
    }

    private void handleDeleteCommand(String[] args) {
        if (args.length < 1) {
            System.err.println("用法: delete <笔记ID>");
            return;
        }
        noteController.deleteNote(args[0]);
    }

    private void handleSearchCommand(String[] args) {
        if (args.length < 1) {
            System.err.println("用法: search <关键词>");
            return;
        }
        noteController.searchNotes(args[0]);
    }

    private void handleTagsCommand(String[] args) {
        tagController.listAllTags();
    }

    private void handleHelpCommand() {
        System.out.println("可用命令:");
        System.out.println("  new <标题> [内容]        - 创建新笔记");
        System.out.println("  list [--tag TAG]        - 列出所有笔记（可按标签过滤）");
        System.out.println("  view <笔记ID>           - 查看笔记详情");
        System.out.println("  edit <笔记ID> <新内容>   - 编辑笔记内容");
        System.out.println("  delete <笔记ID>         - 删除笔记");
        System.out.println("  tag <笔记ID> <标签>     - 添加标签");
        System.out.println("  untag <笔记ID> <标签>   - 移除标签");
        System.out.println("  search <关键词>         - 搜索笔记");
        System.out.println("  tags                    - 列出所有标签");
        System.out.println("  export <笔记ID> <格式> <路径> - 导出单条笔记");
        System.out.println("  export-all <格式> <路径>     - 导出所有笔记");
        System.out.println("  help                    - 显示帮助信息");
        System.out.println("  exit                    - 退出程序");
    }


    private void handleExportCommand(String[] args) {
        if (args.length < 3) {
            System.err.println("用法: export <笔记ID> <格式> <文件路径>");
            System.err.println("格式支持: txt, md, json");
            return;
        }
        noteController.exportNote(args[0], args[1], args[2]);
    }

    private void handleExportAllCommand(String[] args) {
        if (args.length < 2) {
            System.err.println("用法: export-all <格式> <文件路径>");
            System.err.println("格式支持: txt, json");
            return;
        }
        noteController.exportAllNotes(args[0], args[1]);
    }

    private void handleExitCommand() {
        System.out.println("再见！");
        isRunning = false;
    }

    public void close() {
        scanner.close();
    }
}
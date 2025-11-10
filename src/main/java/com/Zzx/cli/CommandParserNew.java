package com.Zzx.cli;


import com.Zzx.cli.command.*;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 重构后的命令解析器 - 使用命令模式
 */
public class CommandParserNew implements CliController {
    private Scanner scanner;
    private boolean isRunning;
    private CommandRegistry commandRegistry;

    public CommandParserNew() {
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.commandRegistry = new CommandRegistry();
        initializeCommands();
    }

    /**
     * 初始化并注册所有命令
     */
    private void initializeCommands() {
        // 注册基本命令
        commandRegistry.registerCommand(new NewCommand());
        commandRegistry.registerCommand(new ListCommand());
        commandRegistry.registerCommand(new ViewCommand());
        commandRegistry.registerCommand(new EditCommand());
        commandRegistry.registerCommand(new DeleteCommand());
        commandRegistry.registerCommand(new TagCommand());
        commandRegistry.registerCommand(new UntagCommand());
        commandRegistry.registerCommand(new SearchCommand());
        commandRegistry.registerCommand(new ExportCommand());
        commandRegistry.registerCommand(new ExportAllCommand());
        commandRegistry.registerCommand(new HelpCommand(commandRegistry));
        commandRegistry.registerCommand(new ExitCommand(this));
        // 注册命令别名
        commandRegistry.registerAlias("quit", "exit");
        commandRegistry.registerAlias("q", "exit");
        commandRegistry.registerAlias("ls", "list");
    }

    /**
     * 解析命令行参数
     */
    public void parseArgs(String[] args) {
        if (args.length == 0) {
            startInteractiveMode();
        } else {
            executeCommand(String.join(" ", args));
        }
    }

    /**
     * 启动交互模式
     */
    private void startInteractiveMode() {
        System.out.println("欢迎使用个人知识管理系统（CLI版）- 重构测试版本");
        System.out.println("输入 help 查看可用命令\n");

        while (isRunning) {
            System.out.print("pkm-New> ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                executeCommand(input);
            }
        }
    }

    /**
     * 执行命令
     */
    private void executeCommand(String commandLine) {
        String[] parts = parseCommandLine(commandLine);
        if (parts.length == 0) return;

        String commandName = parts[0].toLowerCase();
        String[] args = Arrays.copyOfRange(parts, 1, parts.length);

        Command command = commandRegistry.getCommand(commandName);
        if (command != null) {
            try {
                command.execute(args);
            } catch (Exception e) {
                System.out.println("执行命令时出错：" + e.getMessage());
                command.printUsage();
            }
        } else {
            System.out.println("未知命令：" + commandName);
            System.out.println("输入 help 查看可用命令");
        }
    }

    /**
     * 解析命令行（支持引号）
     */
    private String[] parseCommandLine(String commandLine) {
        // 简单的命令行解析，支持带引号的参数
        return commandLine.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }

    /**
     * 设置运行状态
     */
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    /**
     * 获取命令注册器（用于测试）
     */
    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    /**
     * 关闭资源
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}

package com.Zzx.cli.command;


import com.Zzx.cli.CliController;

public class ExitCommand extends AbstractCommand {
    private final CliController controller;

    public ExitCommand(CliController controller) {
        super("exit", "退出程序");
        this.controller = controller;
    }

    @Override
    public void execute(String[] args) {
        System.out.println("感谢使用个人知识管理系统！");
        controller.setRunning(false);
    }

    @Override
    public void printUsage() {
        System.out.println("用法：exit");
        System.out.println("别名：quit");
    }
}
package com.Zzx.cli;



/**
 * 个人知识管理系统 - 重构测试版本入口
 */
public class AppNew {

    public static void main(String[] args) {
        System.out.println("个人知识管理系统 - 重构测试版本");
        System.out.println("===========================\n");

        CommandParserNew parser = new CommandParserNew();

        try {
            // 如果有命令行参数，直接执行命令
            // 如果没有参数，进入交互模式
            parser.parseArgs(args);
        } catch (Exception e) {
            System.err.println("程序运行出错: " + e.getMessage());
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }
}
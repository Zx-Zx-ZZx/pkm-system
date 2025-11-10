package com.Zzx.cli.command;

public interface Command {
    void execute(String[] args);
    String getName();
    String getDescription();
    default void printUsage() {
        System.out.println("用法: " + getName());
    }
}

package com.Zzx.cli.command;


public abstract class AbstractCommand implements Command {
    protected final String name;
    protected final String description;

    protected AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
package com.Zzx;

import com.Zzx.cli.CommandParser;

public class App {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        parser.parseArgs(args);
        parser.close();



    }
}


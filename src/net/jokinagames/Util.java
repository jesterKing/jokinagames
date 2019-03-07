package net.jokinagames;

import java.io.PrintWriter;

public class Util {
    private final static PrintWriter pw = new PrintWriter(System.out, true);

    /**
     * Tulosta UTF-8 merkkijono
     * @param string
     */
    public static void println(String string) {
        pw.println(string);
    }

    public static void print(String string) {
        pw.print(string);
    }

    public static char charFromInt(int nr) {
        return (char)nr;
    }
}

package com.compilador.m6800;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Vars {
    public static String Process(String line) {
        Matcher m = Pattern.compile("VAR\\d\\ +(BYTE|WORD|LONG|DDATA\\.?[BWL]?)\\ +\\$([ABCDEF\\d]+)$").matcher(line);
        while (m.find()) {
            String value = m.group(2);
            switch (m.group(1)) {
            case "BYTE":
                value = Vars.Simple(2, value);
                return value;
            case "WORD":
                value = Vars.Simple(4, value);
                return value;
            case "LONG":
                value = Vars.Simple(8, value);
                return value;
            case "DDATA.B":
                value = Vars.DData("00", value);
                return value;
            case "DDATA.L":
                value = Vars.DData("00000000", value);
                return value;
            default:
                value = Vars.DData("0000", value);
                return value;
            }
        }
        return null;
    }

    private static String DData(String size, String value) {
        String result = "";
        for (int i = 0; i < Integer.parseInt(value, 16); i++) {
            result += size;
        }
        return result.toUpperCase();
    }

    private static String Simple(int size, String value) {
        int extra = 0;
        int zero = (value.length() % size);
        if (zero != 0) {
            extra = size - zero;
        }

        return String.format(String.format("%s0%dx", "%", value.length() + extra), Integer.parseInt(value, 16))
                .toUpperCase();
    }
}

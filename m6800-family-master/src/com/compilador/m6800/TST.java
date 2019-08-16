package com.compilador.m6800;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TST {
    public static String Process(String line) {
        //Matcher m = Pattern.compile("VAR\\d\\ +(BYTE|WORD|LONG|DDATA\\.?[BWL]?)\\ +\\$([ABCDEF\\d]+)$").matcher(line);
        Matcher m = Pattern.compile("TST(\\.B|\\.W|\\.L)?\\ +(([DA][0-7])|(\\([A]\\d\\)\\+?)|(\\-\\([A]\\d\\)))$").matcher(line);
        
        while (m.find()) {
            String value = m.group(2);
            String letter = m.group(1);
            if (letter == null) {
                letter = "";
            }
            switch (letter) {
            case "B":
                return TST.Line("00", m.group(2));
            case "L":
                return TST.Line("10", m.group(2));
            default:
                return TST.Line("01", m.group(2));
            }
        }
        return null;
    }

    private static String Line(String size, String value) {
        String result = "01001010" + size;
        if (value.indexOf("-") != -1) {
            result += "100";
            value = value.replace("-", "");
            value = value.replace("(", "");
            value = value.replace(")", "");
            result += TST.Registro(value);
        } else if (value.indexOf("+") != -1) {
            result += "011";
            value = value.replace("+", "");
            value = value.replace("(", "");
            value = value.replace(")", "");
            result += TST.Registro(value);
        } else if (value.indexOf("(") != -1) {
            result += "010";
            value = value.replace("(", "");
            value = value.replace(")", "");
            result += TST.Registro(value);
        } else if (value.indexOf("A") != -1) {
            result += "001" + TST.Registro(value);
        } else if (value.indexOf("D") != -1) {
            result += "000" + TST.Registro(value);
        }

        return String.format("%x", Integer.parseInt(result, 2)).toUpperCase();
    }

    private static String Registro(String value) {
        if (value.indexOf("A") != -1) {
            value = value.replace("A", "");
            return String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(value))));
        } else if (value.indexOf("D") != -1) {
            value = value.replace("D", "");
            return String.format("%03d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(value))));
        }

        return "";
    }
}

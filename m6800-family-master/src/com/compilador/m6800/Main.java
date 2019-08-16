package com.compilador.m6800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0 || args.length > 1) {
                throw new Exception("Falta el argumento del archivo de prueba.");
            }

            File f = new File(args[0]);
            if (!f.exists() || f.isDirectory()) {
                throw new Exception("El archivo no existe o es un directorio.");
            }

            String preLine = "", process = "";
            Integer sumLines = 0;
            Checksum ch = new Checksum("");
            for (String line : ReadFile(f)) {
                if ((process = Vars.Process(line)) != null) {
                    preLine = String.format("%04x%s00", sumLines, process);
                    ch = new Checksum(preLine);
                } else if((process = TST.Process(line)) != null){
                    preLine = String.format("%04x%s00", sumLines, process);
                    ch = new Checksum(preLine);
                } else {
                    throw new Exception("Error: " + line);
                }

                
                System.out.printf("S1 %s %04x %s %s\n", ch.getNumberPars(), sumLines, process, ch.getChecksum());
                sumLines += Integer.parseInt(ch.getNumberPars(), 16);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] ReadFile(File f) {
        List<String> lines = new ArrayList<String>();

        // Calculate S0
        String nameHexFile = "0000";
        for (char c : f.getName().toCharArray()) {
            nameHexFile += String.format("%02x", (int) c).toUpperCase();
        }

        Checksum ch = new Checksum(nameHexFile);
        nameHexFile = String.format("S0%s%s%s", ch.getNumberPars(), nameHexFile, ch.getChecksum()).toUpperCase();
        System.out.println(nameHexFile);

        // Read file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            String line = reader.readLine();
            while (line != null) {
                if (!line.matches("\\ *(\\.DATA|\\.CODE|END)\\ *") && line.length() > 0) {
                    lines.add(line.toUpperCase());
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines.toArray(new String[0]);
    }
}

package com.compilador.m6800;

public class Checksum {
    private String line;
    private Integer NumberPars = 0;
    private Integer Checksum = 0;

    public Checksum(String line) {
        this.line = line;

        try {
            for (int i = 0; i < this.line.length() - 1; i += 2) {
                this.Checksum += Integer.parseInt(String.format("%s%s", this.line.charAt(i), this.line.charAt(i + 1)),
                        16);
                this.NumberPars++;
            }

            this.Checksum += this.NumberPars;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getChecksum() {
        String hex = String.format("%02x", this.Checksum).toUpperCase();
        hex = String.format("%s%s", hex.charAt(hex.length() - 2), hex.charAt(hex.length() - 1));
        return String.format("%02x", (255 - Integer.parseInt(hex, 16))).toUpperCase();
    }

    public String getNumberPars() {
        return String.format("%02x", this.NumberPars).toUpperCase();
    }
}

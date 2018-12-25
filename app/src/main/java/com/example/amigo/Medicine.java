package com.example.amigo;

public class Medicine {
    private String name;
    private int pillsNum;
    private String time;
    private String warningMsg;

    public Medicine(String name, int pillsNum, String time, String warningMsg) {
        this.name = name;
        this.pillsNum = pillsNum;
        this.time = time;
        this.warningMsg = warningMsg;
    }

    public Medicine() {
        this.name = "";
        this.pillsNum = 0;
        this.time = "";
        this.warningMsg = "";
    }

    public String getName() {
        return name;
    }

    public int getPillsNum() {
        return pillsNum;
    }

    public String getTime() {
        return time;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

}

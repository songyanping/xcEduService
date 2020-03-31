package com.xuecheng.framework.domain.command;

public class CommandVo {

    private  int code;
    private  String log;

    public CommandVo(int code, String log) {
        this.code = code;
        this.log = log;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}

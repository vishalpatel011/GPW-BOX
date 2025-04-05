package com.e.gpwbox;

public class Message {
    public String msgtype, msg, mode,msgid,userid,uname,uenno,udept,time,um;


    public Message() {

    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUenno() {
        return uenno;
    }

    public void setUenno(String uenno) {
        this.uenno = uenno;
    }

    public String getUdept() {
        return udept;
    }

    public void setUdept(String udept) {
        this.udept = udept;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Message(String msgtype, String msg, String mode,String msgid,String userid,String uname,String uenno, String udept,String time,String um) {
        this.msgtype = msgtype;
        this.msg = msg;
        this.mode = mode;
        this.msgid=msgid;
        this.userid=userid;
        this.uname=uname;
        this.uenno=uenno;
        this.udept=udept;
        this.time=time;
        this.um=um;

    }
}
package com.e.gpwbox;

public class facultyrg
{
    public String factname,staffid,fdept,factemail,factmobno,factpass,frole;

    public facultyrg()
    {

    }

    public String getFactname() {
        return factname;
    }

    public void setFactname(String factname) {
        this.factname = factname;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getFdept() {
        return fdept;
    }

    public void setFdept(String fdept) {
        this.fdept = fdept;
    }

    public String getFactemail() {
        return factemail;
    }

    public void setFactemail(String factemail) {
        this.factemail = factemail;
    }

    public String getFactmobno() {
        return factmobno;
    }

    public void setFactmobno(String factmobno) {
        this.factmobno = factmobno;
    }

    public String getFactpass() {
        return factpass;
    }

    public void setFactpass(String factpass) {
        this.factpass = factpass;
    }

    public String getFrole() {
        return frole;
    }

    public void setFrole(String frole) {
        this.frole = frole;
    }

    public facultyrg(String factname, String staffid, String fdept, String factemail, String factmobno, String factpass, String frole)
    {
        this.factname = factname;
        this.staffid = staffid;
        this.fdept= fdept;
        this.factemail = factemail;
        this.factmobno = factmobno;
        this.factpass= factpass;
        this.frole=frole;

    }
}



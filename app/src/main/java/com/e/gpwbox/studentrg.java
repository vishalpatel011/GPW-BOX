package com.e.gpwbox;

public class studentrg
{
    public String name,enno,dept,email,mobileno,password,role,userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnno() {
        return enno;
    }

    public void setEnno(String enno) {
        this.enno = enno;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public studentrg()
    {

    }


    public studentrg(String name, String enno,String dept, String email, String mobileno,String password,String role,String userid) {
        this.name = name;
        this.enno = enno;
        this.dept = dept;
        this.email = email;
        this.mobileno = mobileno;
        this.password = password;
        this.role = role;
        this.userid=userid;

    }
}

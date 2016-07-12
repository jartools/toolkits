package com.bowlong.util;

import java.io.*;
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class Userinfo implements Serializable {
    public int userid = 1; // 用户id
    public String username = ""; // 用户名
    public String pwd = "1"; // 密码
    public double age = 0.0; // 年龄 
    public boolean sex = false; // 性别

    // userid
    public int getUserid() { return userid; }
    public Userinfo setUserid(int userid) { this.userid = userid; return this;}

    // username
    public String getUsername() { return username; }
    public Userinfo setUsername(String username) { this.username = username; return this;}

    // pwd
    public String getPwd() { return pwd; }
    public Userinfo setPwd(String pwd) { this.pwd = pwd; return this;}

    // age
    public double getAge() { return age; }
    public Userinfo setAge(double age) { this.age = age; return this;}

    // sex
    public boolean getSex() { return sex; }
    public Userinfo setSex(boolean sex) { this.sex = sex; return this;}

    public String toString() {
        return toMap().toString();
    }

    public Map toMap() {
        Map result = new HashMap();
        result.put("userid", this.userid);
        result.put("username", this.username);
        result.put("pwd", this.pwd);
        result.put("age", this.age);
        result.put("sex", this.sex);
        return result;
    }

    public Userinfo fromMap(Map map) {
        if(map == null) return this;
        { // userid
            Object obj = map.get("userid");
            this.userid = obj == null ? 0 : (java.lang.Integer) obj;
        }
        { // username
            Object obj = map.get("username");
            this.username = obj == null ? "" : (java.lang.String) obj;
        }
        { // pwd
            Object obj = map.get("pwd");
            this.pwd = obj == null ? "" : (java.lang.String) obj;
        }
        { // age
            Object obj = map.get("age");
            this.age = obj == null ? 0.0 : (java.lang.Double) obj;
        }
        { // sex
            Object obj = map.get("sex");
            this.sex = obj == null ? false : (java.lang.Boolean) obj;
        }
        return this;
    }

    public static Userinfo parse(Map map) {
        if(map == null) return null;
        Userinfo r2 = new Userinfo();
        r2.fromMap(map);
        return r2;
    }

    public Map toHashCodeMap() {
        Map result = new HashMap();
        result.put(-836029914, this.userid);
        result.put(-265713450, this.username);
        result.put(111421, this.pwd);
        result.put(96511, this.age);
        result.put(113766, this.sex);
        return result;
    }

    public Userinfo fromHashCodeMap(Map map) {
        if(map == null) return this;
        { // userid
            Object obj = map.get(-836029914);
            this.userid = obj == null ? 0 : (java.lang.Integer) obj;
        }
        { // username
            Object obj = map.get(-265713450);
            this.username = obj == null ? "" : (java.lang.String) obj;
        }
        { // pwd
            Object obj = map.get(111421);
            this.pwd = obj == null ? "" : (java.lang.String) obj;
        }
        { // age
            Object obj = map.get(96511);
            this.age = obj == null ? 0.0 : (java.lang.Double) obj;
        }
        { // sex
            Object obj = map.get(113766);
            this.sex = obj == null ? false : (java.lang.Boolean) obj;
        }
        return this;
    }

    public static Userinfo parseHashCodeMap(Map map) {
        if(map == null) return null;
        Userinfo r2 = new Userinfo();
        r2.fromHashCodeMap(map);
        return r2;
    }
}


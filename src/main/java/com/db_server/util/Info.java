package com.db_server.util;

/**
 * Created by Navy_ on 2017/3/22.
 */
public class Info {
    private static final String url = "jdbc:mysql://118.178.142.88:3306/";
//    private static final String url = "jdbc:mysql://118.178.142.88:3306/?characterEncoding=utf-8";
    private static final String parameter = "?characterEncoding=utf-8&useSSL=true";
    private static final String name = "com.mysql.jdbc.Driver";
    private static final String sqluser = "CSWEIMS";
    private static final String sqlpassword = "xujunji";

    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
    public String getUser() {
        return sqluser;
    }

    public String getPassword() {
        return sqlpassword;
    }

    public String getParameter() {
        return parameter;
    }

    public static Info instance;

    public static Info getInstance(){
        if (instance ==null){
            synchronized (Info.class){
                if (instance ==null){
                    try {
                        instance =new Info();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

}

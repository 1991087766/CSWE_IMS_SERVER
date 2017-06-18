package com.db_server.util;

import java.sql.*;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by xc on 2017/3/9.
 */
public class MySqlUtil {
    public static MySqlUtil instance;

    private JsonParser parser = new JsonParser();


    protected Connection status = null;

    public static MySqlUtil getInstance(){
        if (instance ==null){
            synchronized (MySqlUtil.class){
                if (instance ==null){
                    try {
                        instance =new MySqlUtil();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return instance;
    }




    /**
     * 数据库连接
     * @return Connection
     */
    public Connection sql_connect(String library){
        try {
            Class.forName(Info.getInstance().getName());
            status = DriverManager.getConnection(
                    Info.getInstance().getUrl()+library+Info.getInstance().getParameter(),
                    Info.getInstance().getUser(),
                    Info.getInstance().getPassword()
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    /**
     * 数据库连接
     * @return Connection
     */
//    public Connection sql_connect(String DataBasesName){
//        try {
//            Class.forName(Info.getInstance().getName());
//            status = DriverManager.getConnection(Info.getInstance().getUrl()+"/"+DataBasesName, Info.getInstance().getUser(), Info.getInstance().getPassword());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return status;
//    }

    /**
     * 关闭服务器连接
     * @param Connection
     */
    public void sql_close(Connection Connection){
        try {
            Connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 关闭服务器连接
     */
    public void sql_close(){
        try {
            status.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 执行语句
     * @param SQL_COMMEND
     * @return PreparedStatement
     */
    private PreparedStatement sql_operation(String SQL_COMMEND){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement =  status.prepareStatement(SQL_COMMEND);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return preparedStatement;
    }

    /**
     * 创建数据库
     * @param DataBasesName
     * @return
     */
    public int sql_create_databases(String DataBasesName){
        int return_data = 0;
        try {
            status.createStatement();
            return_data = sql_operation("CREATE DATABASE "+DataBasesName).executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data;
    }

    /**
     * 创建表:名称、参数类型、数据宽度、是否为空、自动增加、默认值
     * @param SurfaceName
     * @param Parameter
     */
    public int sql_create_surface(String SurfaceName, List Parameter){
        String table = "";
        int return_data = 0;
        try {
            for(int i = 0; i < Parameter.size(); i++)
            {
                if (i < Parameter.size()-1){
                    table = table + Parameter.get(i).toString()+",";
                }else{
                    table = table + Parameter.get(i).toString();
                }
            }
//            System.out.println("CREATE TABLE "+SurfaceName+"("+table+")");
            return_data = status.createStatement().executeUpdate("CREATE TABLE "+SurfaceName+" ("+table+")");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return return_data;
    }


    /**
     * 插入数据
     * @param jsonObject
     * @return 1 = ture, 0 = false;
     */
    public int sql_surface_insert(JsonObject jsonObject){

        String command = "";
        int return_data = 0;
//        System.out.println(jsonObject.toString());
        JsonArray jsonArray = jsonObject.get("SelectValue").getAsJsonArray();
        try {
            sql_connect(jsonObject.get("library").getAsString());
            String table = "";
            for(int i = 0; i < jsonArray.size(); i ++){
                if (i < jsonArray.size()-1){
                    table = table + jsonArray.get(i)+",";
                }else{
                    table = table + jsonArray.get(i);
                }
            }
            command = "("+table+")";

            return_data = status.prepareStatement("INSERT INTO "+jsonObject.get("SurfaceName").getAsString()+" VALUES "+command).executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data;
    }
    public int sql_surface_insert_list(JsonObject jsonObject){

        String command = "";
        int return_data = 0;
        String table = "";
        JsonArray jsonArray = jsonObject.get("SelectValue").getAsJsonArray();
        try {
            sql_connect(jsonObject.get("library").getAsString());
            JsonArray ja;

            for (int list = 0;list< jsonArray.size();list++){
                ja = jsonArray.get(list).getAsJsonArray();
                for (int json = 0; json< ja.size(); json++){

                    if (json> 0){
                        table = table +","+ ja.get(json).toString();
                    }else{
                        table = ja.get(json).getAsString();
                    }
                }
                if(list<jsonArray.size()-1){
                    command = command+"("+table+"),";
                }else {
                    command = command+"("+table+")";
                }
            }

//            System.out.println("导入："+"INSERT INTO "+jsonObject.get("SurfaceName").getAsString()+" VALUES "+command);
            return_data = status.prepareStatement("INSERT INTO "+jsonObject.get("SurfaceName").getAsString()+" VALUES "+command).executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data;
    }



    public JsonArray sql_data_select(JsonObject jsonObject,String nexus1,String nexus2,JsonObject pages){
        JsonArray data = new JsonArray();
        String WHERE = " ";
        String WTIME = " ";
        JsonArray SelectName = jsonObject.get("SelectName").getAsJsonArray();
        JsonArray SelectValue = jsonObject.get("SelectValue").getAsJsonArray();

//        System.out.println("jsonObject:"+SelectName);


        for(int i = 0;i < SelectName.size();i++){
            if(SelectName.size()>1){
                if(i < SelectName.size()-1){
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' "+nexus2+" ";
                }else{
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
                }
            }else{
                WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
            }
        }
//        System.out.println("WHERE:"+WHERE);
        try {

            JsonArray SelectValueCOMMERCIAL = jsonObject.get("SelectValueCOMMERCIAL").getAsJsonArray();
            WTIME += " ( 商业险日期 >= "+SelectValueCOMMERCIAL.get(0).getAsInt()+" AND 商业险日期 <= "+SelectValueCOMMERCIAL.get(1).getAsInt()+") ";
        }catch (Exception r){

        }

        try {
            JsonArray SelectValueCOMPULSORY= jsonObject.get("SelectValueCOMPULSORY").getAsJsonArray();
            WTIME += "AND (交强险日期 >= "+SelectValueCOMPULSORY.get(0).getAsInt()+" AND 交强险日期 <= "+SelectValueCOMPULSORY.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {
            JsonArray SelectValueREGISTER= jsonObject.get("SelectValueREGISTER").getAsJsonArray();
            WTIME += "AND (登记日期 >= "+SelectValueREGISTER.get(0).getAsInt()+" AND 登记日期 <= "+SelectValueREGISTER.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {

            if (jsonObject.get("Binding").getAsBoolean()){
                WTIME = "("+WTIME+") AND "+" 客服 = '客服'";
            }

        }catch (Exception r){

        }
        if(WHERE.replaceAll(" ","").length()>3 && WTIME.replaceAll(" ","").length()>3){
            WHERE = "("+WHERE+") AND "+WTIME;
        }else if (WTIME.replaceAll(" ","").length()>3){
            WHERE = WTIME;
        }else {
            WHERE = WHERE;
        }


        try {
            sql_connect(jsonObject.get("library").getAsString());
            if (WHERE.replaceAll(" ","").replaceAll("\t","").length()!=0){
                WHERE = " WHERE "+WHERE;
            }
//            System.out.println("SELECT:"+"SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);

            ResultSet rs = status.createStatement().executeQuery("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            try {
//                System.out.println("pages:"+pages.toString());
                data = (JsonArray)parser.parse(getSqlDevicesData(rs,getSelect(jsonObject.get("SurfaceName").getAsString()),pages.get("Request").getAsInt(),pages.get("each_page").getAsInt())) ;
            }catch (Exception e){
                data = (JsonArray)parser.parse(getSqlDevicesData(rs,getSelect(jsonObject.get("SurfaceName").getAsString()),1,20)) ;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public JsonArray sql_data_select(JsonObject jsonObject,String nexus1,String nexus2){
        JsonArray data = new JsonArray();
        String WHERE = " ";
        String WTIME = " ";
//        System.out.println("jsonObject:"+jsonObject.toString());
//        System.out.println("SelectName:"+jsonObject.get("SelectName").getAsJsonArray().toString());
//        System.out.println("SelectValue:"+jsonObject.get("SelectValue").getAsJsonArray().toString());

        JsonArray SelectName = jsonObject.get("SelectName").getAsJsonArray();
        JsonArray SelectValue = jsonObject.get("SelectValue").getAsJsonArray();

//        System.out.println("jsonObject:"+SelectName);


        for(int i = 0;i < SelectName.size();i++){
            if(SelectName.size()>1){
                if(i < SelectName.size()-1){
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' "+nexus2+" ";
                }else{
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
                }
            }else{
                WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
            }
        }
//        System.out.println("WHERE:"+WHERE);
        try {

            JsonArray SelectValueCOMMERCIAL = jsonObject.get("SelectValueCOMMERCIAL").getAsJsonArray();
            WTIME += " ( 商业险日期 >= "+SelectValueCOMMERCIAL.get(0).getAsInt()+" AND 商业险日期 <= "+SelectValueCOMMERCIAL.get(1).getAsInt()+") ";
        }catch (Exception r){

        }

        try {
            JsonArray SelectValueCOMPULSORY= jsonObject.get("SelectValueCOMPULSORY").getAsJsonArray();
            WTIME += "AND (交强险日期 >= "+SelectValueCOMPULSORY.get(0).getAsInt()+" AND 交强险日期 <= "+SelectValueCOMPULSORY.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {
            JsonArray SelectValueREGISTER= jsonObject.get("SelectValueREGISTER").getAsJsonArray();
            WTIME += "AND (登记日期 >= "+SelectValueREGISTER.get(0).getAsInt()+" AND 登记日期 <= "+SelectValueREGISTER.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {

            if (jsonObject.get("Binding").getAsBoolean()){
                WTIME = "("+WTIME+") AND "+" 客服 = '客服'";
            }

        }catch (Exception r){

        }
        if(WHERE.replaceAll(" ","").length()>3 && WTIME.replaceAll(" ","").length()>3){
            WHERE = "("+WHERE+") AND "+WTIME;
        }else if (WTIME.replaceAll(" ","").length()>3){
            WHERE = WTIME;
        }else {
            WHERE = WHERE;
        }


        try {
            sql_connect(jsonObject.get("library").getAsString());
            if (WHERE.replaceAll(" ","").replaceAll("\t","").length()!=0){
                WHERE = " WHERE "+WHERE;
            }
//            System.out.println("SELECT:"+"SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);

            ResultSet rs = status.createStatement().executeQuery("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            data = (JsonArray)parser.parse(getSqlDevicesData(rs,getSelect(jsonObject.get("SurfaceName").getAsString()),1,20)) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public JsonArray sql_data_select(JsonObject jsonObject){
        JsonArray data = new JsonArray();
        try {
            sql_connect(jsonObject.get("library").getAsString());
            ResultSet rs = status.createStatement().executeQuery("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString());
            data = (JsonArray)parser.parse(getSqlDevicesData(rs)) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public JsonArray sql_data_select_admin(JsonObject jsonObject){
        JsonArray data = new JsonArray();
        try {
            sql_connect(jsonObject.get("library").getAsString());
            ResultSet rs = status.createStatement().executeQuery(
                    "SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+" WHERE "+jsonObject.get("SelectName").getAsString()+" = "+jsonObject.get("SelectValue").getAsString()
            );
            data = (JsonArray)parser.parse(getSqlDevicesData(rs)) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public JsonArray sql_data_select(JsonObject jsonObject,int a){
        JsonArray data = new JsonArray();
        String WHERE = " ";
        try {
            sql_connect(jsonObject.get("library").getAsString());
//            System.out.println("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            ResultSet rs = status.createStatement().executeQuery("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            data = (JsonArray)parser.parse(getSqlDevicesDataLogin(rs)) ;
//            System.out.println(data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public int sql_data_count(JsonObject jsonObject,String nexus1,String nexus2){
        int data = 0;
        String WHERE = " ";
        String WTIME = " ";
        JsonArray SelectName = jsonObject.get("SelectName").getAsJsonArray();
        JsonArray SelectValue = jsonObject.get("SelectValue").getAsJsonArray();
        for(int i = 0;i < SelectName.size();i++){
            if(SelectName.size()>1){
                if(i < SelectName.size()-1){
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' "+nexus2+" ";
                }else{
                    WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
                }
            }else{
                WHERE += SelectName.get(i).getAsString()+" "+nexus1+" '"+SelectValue.get(i).getAsString()+"' ";
            }
        }
//        System.out.println("WHERE:"+WHERE);
        try {

            JsonArray SelectValueCOMMERCIAL = jsonObject.get("SelectValueCOMMERCIAL").getAsJsonArray();
            WTIME += " ( 商业险日期 >= "+SelectValueCOMMERCIAL.get(0).getAsInt()+" AND 商业险日期 <= "+SelectValueCOMMERCIAL.get(1).getAsInt()+") ";
        }catch (Exception r){

        }

        try {
            JsonArray SelectValueCOMPULSORY= jsonObject.get("SelectValueCOMPULSORY").getAsJsonArray();
            WTIME += "AND (交强险日期 >= "+SelectValueCOMPULSORY.get(0).getAsInt()+" AND 交强险日期 <= "+SelectValueCOMPULSORY.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {
            JsonArray SelectValueREGISTER= jsonObject.get("SelectValueREGISTER").getAsJsonArray();
            WTIME += "AND (登记日期 >= "+SelectValueREGISTER.get(0).getAsInt()+" AND 登记日期 <= "+SelectValueREGISTER.get(1).getAsInt()+") ";
        }catch (Exception r){

        }
        try {

            if (jsonObject.get("Binding").getAsBoolean()){
                WTIME = "("+WTIME+") AND "+" 客服 = '客服'";
            }

        }catch (Exception r){

        }
        if(WHERE.replaceAll(" ","").length()>3 && WTIME.replaceAll(" ","").length()>3){
            WHERE = "("+WHERE+") AND "+WTIME;
        }else if (WTIME.replaceAll(" ","").length()>3){
            WHERE = WTIME;
        }else {
            WHERE = WHERE;
        }
        try {
            sql_connect(jsonObject.get("library").getAsString());
            if (WHERE.replaceAll(" ","").replaceAll("\t","").length()!=0){
                WHERE = " WHERE "+WHERE;
            }

//            System.out.println("COUNT:"+"SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            ResultSet rs  = status.createStatement().executeQuery("SELECT * FROM "+jsonObject.get("SurfaceName").getAsString()+WHERE);
            rs.last();
            data = rs.getRow();
//            System.out.println("COUNT:"+data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    /**
     * 修改数据
     * @param jsonObject1
     * @return
     */
    public int sql_data_alter(JsonObject jsonObject1){
        int return_data = 0;
        String SET = "";
        String WHERE = "";

        if(jsonObject1.get("SelectName").isJsonArray()){
            JsonArray SelectName = jsonObject1.get("SelectName").getAsJsonArray();
            JsonArray SelectValue = jsonObject1.get("SelectValue").getAsJsonArray();
            for(int i = 0;i < SelectName.size();i++){
                if(SelectName.size()>1){
                    if(i < SelectName.size()-1){
                        WHERE += SelectName.get(i).getAsString()+" like '"+SelectValue.get(i).getAsString()+"' OR ";
                    }else{
                        WHERE += SelectName.get(i).getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
                    }
                }else{
                    WHERE += SelectName.get(i).getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
                }
            }
        }else {
            JsonArray SelectValue = jsonObject1.get("SelectValue").getAsJsonArray();
            for(int i = 0;i < SelectValue.size();i++){
                if(SelectValue.size()>1){
                    if(i < SelectValue.size()-1){
                        WHERE += jsonObject1.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' OR ";
                    }else{
                        WHERE += jsonObject1.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
                    }
                }else{
                    WHERE += jsonObject1.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
                }
            }
        }
        if(jsonObject1.get("ColumnName").isJsonArray()){
            for(int i = 0; i<jsonObject1.get("ColumnName").getAsJsonArray().size();i++){
                if(jsonObject1.get("ColumnName").getAsJsonArray().size()>1){
                    if(i < jsonObject1.get("ColumnName").getAsJsonArray().size()-1){
                        SET += jsonObject1.get("ColumnName").getAsJsonArray().get(i).getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"',";
                    }else{
                        SET += jsonObject1.get("ColumnName").getAsJsonArray().get(i).getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"'";
                    }
                }else{
                    SET += jsonObject1.get("ColumnName").getAsJsonArray().get(i).getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"'";
                }
            }
        }else {
            for(int i = 0; i<jsonObject1.get("Value").getAsJsonArray().size();i++){
                if(jsonObject1.get("Value").getAsJsonArray().size()>1){
                    if(i < jsonObject1.get("Value").getAsJsonArray().size()-1){
                        SET += jsonObject1.get("ColumnName").getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"',";
                    }else{
                        SET += jsonObject1.get("ColumnName").getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"'";
                    }
                }else{
                    SET += jsonObject1.get("ColumnName").getAsString()+"='"+jsonObject1.get("Value").getAsJsonArray().get(i).getAsString()+"'";
                }
            }
        }


        try {
            sql_connect(jsonObject1.get("library").getAsString());
//            System.out.println("UPDATE "+jsonObject1.get("SurfaceName").getAsString()+" SET "+SET+" WHERE "+WHERE);
            return_data = status.createStatement().executeUpdate("UPDATE "+jsonObject1.get("SurfaceName").getAsString()+" SET "+SET+" WHERE "+WHERE);
//            System.out.println("return_data:"+return_data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data ;
    }

    /**
     * 删除表内数据
     * Value  UNIX时间戳，是文件名
     * @return
     */
    //delete from (表名) where (列名)=(值)
    public int sql_data_del(JsonObject jsonObject){
        int return_data = 0;
        String WHERE = "";

        WHERE = jsonObject.get("SelectName").getAsString()+" like '"+jsonObject.get("SelectValue").getAsString()+"' ";
        try {
            sql_connect(jsonObject.get("library").getAsString());
//            System.out.println("DELETE FROM "+jsonObject.get("SurfaceName").getAsString()+" WHERE "+WHERE);
            return_data = status.createStatement().executeUpdate("DELETE FROM "+jsonObject.get("SurfaceName").getAsString()+" WHERE "+WHERE);

//            System.out.println("return_data:"+return_data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data ;
    }
    public boolean sql_data_del(JsonObject jsonObject,String SurfaceNameA,String SurfaceNameB){
        int return_data = 0;
        String WHERE = "";

        JsonArray SelectValue = jsonObject.get("SelectValue").getAsJsonArray();
        for(int i = 0;i < SelectValue.size();i++){
            if(SelectValue.size()>1){
                if(i < SelectValue.size()-1){
                    WHERE += jsonObject.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' OR ";
                }else{
                    WHERE += jsonObject.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
                }
            }else{
                WHERE += jsonObject.get("SelectName").getAsString()+" like '"+SelectValue.get(i).getAsString()+"' ";
            }
        }


        try {
            sql_connect(jsonObject.get("library").getAsString());
//            System.out.println("INSERT INTO "+SurfaceNameB+" SELECT * FROM "+SurfaceNameA+" WHERE "+WHERE);
            return_data = status.prepareStatement("INSERT INTO "+SurfaceNameB+" SELECT * FROM "+SurfaceNameA+" WHERE "+WHERE).executeUpdate();
//            System.out.println("DELETE FROM "+SurfaceNameB+" WHERE "+WHERE);
            return_data = status.createStatement().executeUpdate("DELETE FROM "+SurfaceNameA+" WHERE "+WHERE);

//            System.out.println("return_data:"+return_data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true ;
    }

    /**
     * 删除表
     * @param SurfaceName
     * @return
     */
    public int sql_surface_del(String SurfaceName){
        int return_data = 0;
        try {
            status.createStatement();
            return_data = sql_operation("DROP TABLE "+SurfaceName).executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return return_data;
    }


    public int sql_data_update(String SurfaceName ,String column,String condition){
        int data = 0;
        try {
            PreparedStatement pst = status.prepareStatement("UPDATE "+SurfaceName+" SET "+column+" WHERE "+condition);
            data = pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return data;
    }



    public String getSqlDevicesData(ResultSet rs, int select,int Request,int each_page){
        String data = "[";
        int i = 0;
        int No = 0;
        try {
            switch (select) {
                case 0:
                    try {
                        while (rs.next()){
                            data = data+"{" +
                                    "value:"+rs.getInt(1)+"}";
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 1:
                    try {
                        while (rs.next()){
                            data = data+"{" +
                                    "value:"+rs.getString(1)+"}";
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 2:
                    try{
                        while (rs.next()) {

                            if(i>=1){
                                data=data+",";
                            }
                            if (i>= each_page*(Request-1)&&i<each_page*Request-1)
                            data = data+"{" +
                                    "\"编号\":\""+rs.getString(1)+"\"," +
                                    "\"账号\":\""+rs.getString(2)+"\"," +
                                    "\"密码\":\""+rs.getString(3)+"\"," +
                                    "\"性别\":\""+rs.getString(4)+"\"," +
                                    "\"混编\":\""+rs.getString(5)+"\"," +
                                    "\"部门\":\""+rs.getString(6)+"\"," +
                                    "\"地址\":\""+rs.getString(7)+"\"," +
                                    "\"手机\":\""+rs.getString(8)+"\"," +
                                    "\"邮件\":\""+rs.getString(9)+"\"," +
                                    "\"头像\":\""+rs.getString(10)+"\"," +
                                    "\"管理员\":"+rs.getInt(11)+","+
                                    "\"姓名\":\""+rs.getString(12)+"\"," +
                                    "\"更新时间\":\""+rs.getString(13) +
                                    "\"}";
                            i++;
                        }
                    }catch (Exception e){
                        return "DB Error";
                    }

                    break;
                case 3:

                    try{
                        while (rs.next()) {
                            if(i>=1){
                                data=data+",";
                            }
                            if (i>= each_page*(Request-1)&&i<each_page*Request-1)
                            data = data+"{" +
                                    "\"USERNAME\":\""+rs.getString(1)+"\"," +
                                    "\"ACCESS_TOKEN\":\""+rs.getString(2)+"\"," +
                                    "\"IP\":\""+rs.getString(3)+"\"," +
                                    "\"ADDRESS\":\""+rs.getString(4)+"\"," +
                                    "\"TIME\":\""+rs.getString(5)+"\"}";
                            i++;
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 4:
                    try{
                        while (rs.next()) {

                            if (i>= each_page*(Request-1)&&i<each_page*Request-1){
                                if(i>each_page*(Request-1)){
                                    data=data+",";
                                }
                                data = data+"{" +
                                        "\"ID\":\""+rs.getString(1)+"\"," +
                                        "\"DT\":\""+rs.getString(2)+"\"," +
                                        "\"USERNAME\":\""+rs.getString(3)+"\"," +
                                        "\"DEPARTMENT\":\""+rs.getString(4)+"\"," +
                                        "\"ADDRESS\":\""+rs.getString(5)+"\"," +
                                        "\"IP\":\""+rs.getString(6)+"\"," +
                                        "\"CHECK\":\""+rs.getString(7) +
                                        "\"}";
                            }


                            i++;
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 5:
                    try{
                        while (rs.next()) {
                            if (i>= each_page*(Request-1)&&i<each_page*Request-1) {
                                if (i > each_page * (Request - 1)) {
                                    data = data + ",";
                                }
                                data = data + "{" +
                                        "\"ID\":\"" + rs.getString(1) + "\"," +
                                        "\"DT\":\"" + rs.getString(2) + "\"," +
                                        "\"USERNAME\":\"" + rs.getString(3) + "\"," +
                                        "\"DEPARTMENT\":\"" + rs.getString(4) + "\"," +
                                        "\"ADDRESS\":\"" + rs.getString(5) + "\"," +
                                        "\"IP\":\"" + rs.getString(6) +
                                        "\"}";
                            }
                            i++;
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 6:
                    try{
                        while (rs.next()) {
                            if (i>= each_page*(Request-1)&&i<each_page*Request-1){
                                if(i>each_page*(Request-1)){
                                    data=data+",";
                                }
                                data = data+"{" +
                                        "\"ID\":\""+rs.getString(1)+"\"," +
                                        "\"SOURCE\":\""+rs.getString(2)+"\"," +
                                        "\"THEME\":\""+rs.getString(3)+"\"," +
                                        "\"STATE\":\""+rs.getInt(4)+"\"," +
                                        "\"}";
                            }

                            i++;
                        }
                    }catch (Exception e){
                        return "[]";
                    }

                    break;
                case 7:
                    try{
                        while (rs.next()) {

                            if (i>= each_page*(Request-1)&&i<each_page*Request){
                                if(i>each_page*(Request-1)){
                                    data=data+",";
                                }
                                No++;
                                data = data+"{" +
                                        "\"No.\":"+No+"," +
                                        "\"编号\":\""+rs.getString(1)+"\"," +
                                        "\"车牌号\":\""+rs.getString(2)+"\"," +
                                        "\"车架号\":\""+rs.getString(3)+"\"," +
                                        "\"客户名称\":\""+rs.getString(4)+"\"," +
                                        "\"品牌\":\""+rs.getString(5)+"\"," +
                                        "\"车型号\":\""+rs.getString(6)+"\"," +
                                        "\"发动机号\":\""+rs.getString(7)+"\"," +
                                        "\"固定电话\":\""+rs.getString(8)+"\"," +
                                        "\"手机\":\""+rs.getString(9)+"\"," +
                                        "\"身份证号\":\""+rs.getString(10)+"\"," +
                                        "\"车座位\":"+rs.getInt(11)+"," +
                                        "\"商业险日期\":"+rs.getLong(12)+"," +
                                        "\"交强险日期\":"+rs.getLong(13)+"," +
                                        "\"登记日期\":"+rs.getLong(14)+"," +
                                        "\"地址\":\""+rs.getString(15)+"\","+
                                        "\"状态\":\""+rs.getString(16)+"\","+
                                        "\"备注\":\""+rs.getString(17)+"\","+
                                        "\"汽车价格\":\""+rs.getString(18)+"\","+
                                        "\"初保\":\""+rs.getString(19)+"\","+
                                        "\"客服\":\""+rs.getString(20)+"\","+
                                        "\"更新时间\":\""+rs.getString(21)+
                                        "\"}";
                            }

                            i++;
                        }
                        No=0;

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        return "[]";
                    }

                    break;
                case 11:
                    try{
                        while (rs.next()) {

                            if (i>= each_page*(Request-1)&&i<each_page*Request){
                                if(i>each_page*(Request-1)){
                                    data=data+",";
                                }
                                No++;
                                data = data+"{" +
                                        "\"No.\":"+No+"," +
                                        "\"编号\":\""+rs.getString(1)+"\"," +
                                        "\"姓名\":\""+rs.getString(2)+"\"," +
                                        "\"部门\":\""+rs.getString(3)+"\"," +
                                        "\"管理\":\""+rs.getString(4)+"\"," +
                                        "\"状态\":\""+rs.getString(5)+"\"," +
                                        "\"入职时间\":\""+rs.getString(6)+"\"," +
                                        "\"离职时间\":\""+rs.getString(7)+"\"," +
                                        "\"地址\":\""+rs.getString(8)+
                                        "\"}";
                            }

                            i++;
                        }
                        No=0;

                    }catch (Exception e){
                        System.out.println(e.getMessage());
                        return "[]";
                    }

                    break;
            }

            data=data+"]";
            rs.close();
            sql_close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public String getSqlDevicesData(ResultSet rs){
        String data = "[";
        int i = 0;
        try {
            try{
                while (rs.next()) {
                    if(i>=1){
                        data=data+",";
                    }
                    data = data+rs.getString(12);

                    i++;
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
                return "[]";
            }

            data=data+"]";

            rs.close();
            sql_close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public String getSqlDevicesDataLogin(ResultSet rs){
        String data = "[";
        int i = 0;
        try {
            try{
                while (rs.next()) {

                    if(i>0){
                        data=data+",";
                    }
                    data = data+"{" +
                            "\"USERNAME\":\""+rs.getString(1)+"\"," +
                            "\"ACCESS_TOKEN\":\""+rs.getString(2)+"\"," +
                            "\"IP\":\""+rs.getString(3)+"\"," +
                            "\"ADDRESS\":\""+rs.getString(4)+"\"," +
                            "\"TIME\":\""+rs.getString(5)+"\"}";


                    i++;
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
                return "[]";
            }

            data=data+"]";

            rs.close();
            sql_close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public int getSelect(String SurfaceName){
        int select=0;
        if (SurfaceName.equals("db_info")){
            select = 2;
        }else if(SurfaceName.equals("db_login")){
            select = 3;
        }else if(SurfaceName.equals("db_log_check")){
            select = 4;
        }else if(SurfaceName.equals("db_log")){
            select = 5;
        }else if(SurfaceName.equals("db_new")){
            select = 6;
        }else if(SurfaceName.equals("db_customer_all")){
            select = 7;
        }else if(SurfaceName.equals("del_info")){
            select = 8;
        }else if(SurfaceName.equals("del_customer")){
            select = 9;
        }else if(SurfaceName.equals("del_new")){
            select = 10;
        }else if(SurfaceName.equals("db_department")){
            select = 11;
        }
        return select;
    }


    public static void json(){
//        "[{'a1','a2','a3'},{'b1','b2','b3'},{'c1','c2','c3'}]"
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray)jsonParser.parse("[{'a1','a2','a3'},{'b1','b2','b3'},{'c1','c2','c3'}]");
        System.out.println(jsonArray);
        JsonObject jsonObject ;
        JsonElement jsonElement ;
        for (int json = 0; json<jsonArray.size(); json++){
            jsonElement = jsonArray.get(json);
            jsonObject = jsonElement.getAsJsonObject();
//            System.out.println(jsonObject);
        }
    }

    public static  void main(String[] ages){
//        String[] data = {"'id2'","'name123'","'pwd123'","123","'num123'","'dep123'","'add123'","'phone123'","'mail123'","'icon123'"};
//        MySqlUtil.getInstance().sql_connect();
//        MySqlUtil.getInstance().sql_create_databases("QA_SPMS");
//        MySqlUtil.getInstance().sql_close();
//        MySqlUtil.getInstance().sql_connect("QA_SPMS");
//        MySqlUtil.getInstance().sql_surface_insert("01_userinfo","info",data);

//        String a = MySqlUtil.getInstance().sql_data_select((JsonObject)new JsonParser().parse("{\"library\":\"01_userinfo\",\"SurfaceName\":\"info\",\"SelectName\":[\"ID\"],\"SelectValue\":[\"xujunji\"]}"));
//        String a = MySqlUtil.getInstance().sql_data_select("02_news","new", "'%a1%'");
//
////        String a = MySqlUtil.getInstance().sql_data_select("QRCODE_TEST","2","*",3);
////        String a = MySqlUtil.getInstance().sql_data_select("QRCODE_CODE","1","*",3);
////        int a = MySqlUtil.getInstance().sql_data_del("QRCODE_TEST","12");
////        String a = MySqlUtil.getInstance().sql_data_select("QRCODE_COUNT","'qrcode_add'","COUNT",0);
//        System.out.println(a);
//        int b = Integer.parseInt(a.substring(a.indexOf("return:")+7,a.indexOf("}")));
//        MySqlUtil.getInstance().sql_data_alter("QRCODE_COUNT","qrcode_add",b+1);
//        MySqlUtil.getInstance().sql_close();
//        MySqlUtil.getInstance().sql_data_alter_autoadd("QRCODE_COUNT","'qrcode_add'","COUNT");

    }
}
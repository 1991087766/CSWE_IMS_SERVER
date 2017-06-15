package com.db_server;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by NAVY on 2017/6/3.
 */
public class demo {
    public static void main(String[] a){
        UUID uuid = UUID.randomUUID();
        System.out.println((JsonArray)new JsonParser().parse("[{'ID':'null','USERNAME':'xujunji','PWD':'1ba97e68f198520852f5adabeb4e5e54','GENDER':'null','NUMBER':'null','CUSTOMER_SERVICE':'徐君基'}]"));
    }

//    private static long Date2TimeStamp(String dateStr) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
//            return sdf.parse(dateStr).getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}

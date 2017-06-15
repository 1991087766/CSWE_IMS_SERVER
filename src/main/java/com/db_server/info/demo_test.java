package com.db_server.info;

import com.db_server.util.MessageCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by xc on 2017/6/12.
 */
@Controller
public class demo_test {
    @RequestMapping(value="test")
    @ResponseBody
    public String test_demo(){
        return MessageCode.getInstance().getCode_1001000().toString();
    }
}

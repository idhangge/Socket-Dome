package com.i2f.paycore.controller;

import com.i2f.paycore.model.data.Test2;
import com.i2f.paycore.model.xmlP.XmlPackage;
import com.i2f.paycore.utils.BankClient;
import com.i2f.paycore.utils.StringDateUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    public static void main(String[] args) throws IOException {
        String serialNum= new StringDateUtils().StringDate();
        Test2 test2 = new Test2();
        test2.setAmount("100");
        test2.setCardNumber("11111111111111111");
        XmlPackage xmlPackage = BankClient.executeTran("9527", test2,serialNum);
        Object bodyData = xmlPackage.getBodyData();
        System.out.println(bodyData);
    }

}

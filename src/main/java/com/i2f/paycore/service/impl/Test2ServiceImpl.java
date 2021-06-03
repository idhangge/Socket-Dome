package com.i2f.paycore.service.impl;

import com.i2f.paycore.model.data.Test2;
import com.i2f.paycore.model.xmlP.XmlPackage;
import com.i2f.paycore.service.Test2Service;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description :
 * @Author : ch
 * @Date: 2021-06-01
 */
@Slf4j
@Service
public class Test2ServiceImpl implements Test2Service {

    @SneakyThrows
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test2(XmlPackage xmlPackage) {

        Test2 bodyData = (Test2) xmlPackage.getBodyData();
        String amount = bodyData.getAmount();
        String cardNumber = bodyData.getCardNumber();
        log.info("amount:----- "+amount+" cardNumber===="+cardNumber);
        //返回
        xmlPackage.setBodyData(true);


    }
}


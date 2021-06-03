package com.i2f.paycore.model.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//data.model下放传输接收参数封装
@Data
@AllArgsConstructor
@NoArgsConstructor
//每封装一个加此注解给此类起别名(别名最好与实体类名一致)，切记一定加上
//如果XStream解析失败 别名需要改成类的路径
@XStreamAlias("com.i2f.paycore.model.data.Test2")
public class Test2 {
    private String amount;
    private String cardNumber;
}

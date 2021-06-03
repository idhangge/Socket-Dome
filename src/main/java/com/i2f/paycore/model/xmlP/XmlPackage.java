package com.i2f.paycore.model.xmlP;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * XML报文统一格式类
 */
@XStreamAlias("XmlPackage")
public class XmlPackage<T> {

    private Head head = new Head();
    private Body body = new Body();

    public String getHeadCode() {
        return head == null ? null : head.code;
    }

    public void setHeadCode(String code) {
        head.code = code;
    }

    public String getHeadMessage() {
        return head == null ? null : head.message;
    }

    public void setHeadMessage(String message) {
        head.message = message;
    }

    public String getHeadSerialNum() {
        return head == null ? null : head.serialNum;
    }

    public void setHeadSerialNum(String serialNum) {
        head.serialNum = serialNum;
    }

    public String getHeadType() {
        return head == null ? null : head.type;
    }

    public void setHeadType(String type) {
        head.type = type;
    }

    public T getBodyData() {
        return body == null ? null : body.data;
    }

    public void setBodyData(T data) {
        body.data = data;
    }

    public List<T> getBodyDataList() {
        return body == null ? null : body.dataList;
    }

    public void setBodyDataList(List<T> data) {
        body.dataList = data;
    }

    /**
     * 报文头部信息
     */
    private class Head{
        /**
         * 序列号  随机生成
         */
        private String serialNum;
        /**
         * 处理类型  交易类型
         */
        private  String type;
        /**
         * 状态码
         */
        private String code;
        /**
         * 消息
         */
        private String message;
    }

    /**
     * 报文数据  统一返回格式 responseResult
     */
    private class Body{
        /**
         * 数据
         */
        private T data;

        /**
         * 集合数据
         */
        private List<T> dataList;

    }

}

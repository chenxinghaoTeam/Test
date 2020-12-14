package com.runApplication.controller;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class Test {
	public static void main(String[] args) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		// url为调用webService的wsdl地址
        Client client = dcf.createClient("http://localhost:8081/chenxinghao/test?wsdl");
        // namespace是命名空间，methodName是方法名
        QName name = new QName("http://client.runApplication.com/", "sayHello");
        // paramvalue为参数值
        Object[] objects;
        try {
            objects = client.invoke(name, "陈泽希");
            System.out.println(objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
}

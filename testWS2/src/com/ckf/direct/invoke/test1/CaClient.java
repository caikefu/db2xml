package com.ckf.direct.invoke.test1;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;

public class CaClient {
    public static void main(String[] args) {  
        try {
            String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?wsdl";
            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR); 
            options.setAction("http://WebXml.com.cn/getWeatherbyCityName");//需要加上这条语句 
            //options.setProperty(HTTPConstants.CHUNKED, "true");
            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            String tns = "http://WebXml.com.cn/";
            OMNamespace omNs = fac.createOMNamespace(tns, "");
            OMElement method = fac.createOMElement("getWeatherbyCityName", omNs);
            OMElement symbol = fac.createOMElement("theCityName", omNs);
            symbol.addChild(fac.createOMText(symbol, ""));
            method.addChild(symbol);
            method.build();
            OMElement result = sender.sendReceive(method);
            System.out.println(result);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } 
        
        try {
            String url = "http://www.webxml.com.cn/WebServices/IpAddressSearchWebService.asmx?wsdl";
            Options options = new Options();
            EndpointReference targetEPR = new EndpointReference(url);
            options.setTo(targetEPR); 
            options.setAction("http://WebXml.com.cn/getCountryCityByIp");//需要加上这条语句 
            //options.setProperty(HTTPConstants.CHUNKED, "true");
            ServiceClient sender = new ServiceClient();
            sender.setOptions(options);
            OMFactory fac = OMAbstractFactory.getOMFactory();
            String tns = "http://WebXml.com.cn/";
            OMNamespace omNs = fac.createOMNamespace(tns, "");
            OMElement method = fac.createOMElement("getCountryCityByIp", omNs);
            OMElement symbol = fac.createOMElement("theIpAddress", omNs);
            symbol.addChild(fac.createOMText(symbol, "36.7.71.120"));
            method.addChild(symbol);
            method.build();
            OMElement result = sender.sendReceive(method);
            System.out.println(result);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } 
        
//        try {
//            String url = "目标URL";
//            Options options = new Options();
//            EndpointReference targetEPR = new EndpointReference(url);
//            options.setTo(targetEPR);
//            options.setAction("目标的TargetNameSpace"+"调用的方法名");//需要加上这条语句
//            ServiceClient sender = new ServiceClient();
//            sender.setOptions(options);
//            OMFactory fac = OMAbstractFactory.getOMFactory();
//            String tns = "目标的TargetNameSpace";
//            OMNamespace omNs = fac.createOMNamespace(tns, "");
//            OMElement method = fac.createOMElement("调用的方法名", omNs);
//            OMElement symbol = fac.createOMElement("参数名", omNs);
//            symbol.addChild(fac.createOMText(symbol, "参数值"));
//            method.addChild(symbol);
//            method.build();
//            OMElement result = sender.sendReceive(method);
//            System.out.println(result);
//        } catch (AxisFault axisFault) {
//            axisFault.printStackTrace();
//        }
    }
}

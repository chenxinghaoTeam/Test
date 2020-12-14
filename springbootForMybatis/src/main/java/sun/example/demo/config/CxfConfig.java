//package sun.example.demo.config;
//
//import javax.xml.ws.Endpoint;
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.bus.spring.SpringBus;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.apache.cxf.transport.servlet.CXFServlet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import sun.example.demo.dao.UserMapper;
//
//
//@Configuration
//public class CxfConfig {
//
//	@Autowired
//	private Bus bus;
//
//	@Autowired
//	UserMapper service;
//
//	/**
//	 * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
//	 * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
//	 * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/user?wsdl
//	 *
//	 * @return
//	 */
//	@Bean
//	public ServletRegistrationBean disServlet() {
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(),
//				"/chenzexi/*");
//		return servletRegistrationBean;
//	}
//
//	@Bean(name = Bus.DEFAULT_BUS_ID)
//	public SpringBus springBus() {
//		return new SpringBus();
//	}
//
//	/**
//	 * JAX-WS 站点服务
//	 **/
//	@Bean
//	public Endpoint endpoint() {
//		EndpointImpl endpoint = new EndpointImpl(bus, service);
//		endpoint.publish("/zexi");
//		return endpoint;
//	}
//
//}

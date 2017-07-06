package cn.itcast.cxf.ws.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import cn.itcast.cxf.domain.User;
import cn.itcast.cxf.service.IUserService;

public class WS_Client {
	public static void main(String[] args) {
		// 编写客户端 调用发布WebService服务
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(IUserService.class);
		jaxWsProxyFactoryBean.setAddress("http://localhost:9999/userService");

		jaxWsProxyFactoryBean.getInInterceptors().add(
				new LoggingInInterceptor());
		jaxWsProxyFactoryBean.getOutInterceptors().add(
				new LoggingOutInterceptor());

		// 创建调用远程服务代理对象
		IUserService proxy = (IUserService) jaxWsProxyFactoryBean.create();
		// 调用代理对象 任何一个方法，都将通过网络 调用web服务
		System.out.println(proxy.sayHello("传智播客"));

		User user = new User();
		user.setUsername("xiaoming");
		System.out.println(proxy.findCarsByUser(user));
	}
}

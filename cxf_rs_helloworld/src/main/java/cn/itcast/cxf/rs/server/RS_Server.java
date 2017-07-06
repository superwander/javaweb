package cn.itcast.cxf.rs.server;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import cn.itcast.cxf.domain.Car;
import cn.itcast.cxf.domain.User;
import cn.itcast.cxf.service.IUserService;
import cn.itcast.cxf.service.UserServiceImpl;

public class RS_Server {
	public static void main(String[] args) {
		// 创建业务接口 实现类对象
		IUserService userService = new UserServiceImpl();
		// 服务器FactoryBean 创建服务
		JAXRSServerFactoryBean jaxrsServerFactoryBean = new JAXRSServerFactoryBean();
		jaxrsServerFactoryBean.setResourceClasses(User.class, Car.class); // 将哪些实体数据转换xml、json发送
		jaxrsServerFactoryBean.setServiceBean(userService);
		jaxrsServerFactoryBean.setAddress("http://localhost:9997");

		// 发布服务
		jaxrsServerFactoryBean.create();
	}
}

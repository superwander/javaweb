package cn.itcast.cxf.rs.client;

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import cn.itcast.cxf.domain.User;

public class RS_Client {
	public static void main(String[] args) {
		// create 建立与调用 服务资源路径 连接
		// type 发送给服务器数据格式 --- @Consumes
		// accept 接收服务器传输数据格式 ---- @Produces
		// 采用HTTP协议哪种方式访问服务器
		Collection<? extends User> collection = WebClient
				.create("http://localhost:9997/userService/user")
				.accept(MediaType.APPLICATION_XML).getCollection(User.class);
		System.out.println(collection);

		// 添加用户
		User user = new User();
		WebClient.create("http://localhost:9997/userService/user")
				.type(MediaType.APPLICATION_JSON).post(user);

		// 查询某个用户，获取json
		User resultUser = WebClient
				.create("http://localhost:9997/userService/user/1")
				.accept(MediaType.APPLICATION_JSON).get(User.class);
		System.out.println(resultUser);
	}
}

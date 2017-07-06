package cn.itcast.crm.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

/**
 * 客户关联信息接口
 * 
 * @author 王伟    15349278089
 * @version 1.0, 2017-7-6 15:56:04
 */

//@Path("/customerService")
//@Produces("*/*")
public interface CustomerService {

	//查询所有未关联客户列表
	@Path("/noassociationcustomers")
	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findNoAssociationCustomers();
	
	//已经关联到指定区的客户列表
	@Path("/associationfixedareacustomers/{fixedareaid}")
	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Customer> findHasAssociationFixedAreaCustomers(
			@PathParam("fixedAreaId") String fixedAreaId);
	
	// 将客户关联到定区上，将所有客户id 平成字符串 1,2,3
	@Path("/associationcustomerstofixedarea")
	@GET
	@Produces
	public void associationCustomersToFixedArea(
			@QueryParam("customerIdStr") String customerIdStr,
			@QueryParam("fixedAreaId") String fixedAreaId);
			
}

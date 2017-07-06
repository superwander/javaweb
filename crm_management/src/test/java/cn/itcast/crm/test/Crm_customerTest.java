package cn.itcast.crm.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class Crm_customerTest {

	@Resource
	private CustomerService customerServiceImpl;
	
	@Test
	public void test1(){
		List<Customer> customers = customerServiceImpl.findNoAssociationCustomers();
		System.out.println(customers);
	}
	
	@Test
	public void  test2(){
		customerServiceImpl.associationCustomersToFixedArea("1,2","1");
	}
	
	@Test
	public void test3(){
		List<Customer> customers = customerServiceImpl.findHasAssociationFixedAreaCustomers("1");
		System.out.println(customers);
	}
}

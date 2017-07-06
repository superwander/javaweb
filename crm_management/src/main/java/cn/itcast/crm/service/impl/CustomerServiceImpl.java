package cn.itcast.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerDao;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * CustomerService实现类
 * 
 * @author 王伟
 * @version 1.0, 2017-7-6 18:16:57
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	@Override
	public List<Customer> findNoAssociationCustomers() {
		return customerDao.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findHasAssociationFixedAreaCustomers(
			String fixedAreaId) {
		return customerDao.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void associationCustomersToFixedArea(String customerIdStr,
			String fixedAreaId) {
		String[] idStrs = customerIdStr.split(",");
		for (String id : idStrs) {
			customerDao.updateFixedAreaId(Integer.valueOf(id),fixedAreaId);
		}
		
	}

}

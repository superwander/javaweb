package cn.itcast.bos.service.base.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.exception.CourierException;
import cn.itcast.bos.service.base.CourierService;

/**
 * CourierService实现类
 * 
 * @author 王伟  
 * @version 1.0, 2017-7-2 18:45:54
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

	@Resource
	private CourierDao courierDaoImpl;
	
	@Override
	public void save(Courier courier) throws CourierException {
		//判断是否存在快递员Number，存在抛出一个异常
		Courier courier1 = courierDaoImpl.findByCourierNum(courier.getCourierNum());
		if(courier1==null){
			courierDaoImpl.save(courier);
		}else {
			throw new CourierException(courier.getCourierNum()+"已经存在不能加入到数据库，请重新输入员工编号");
		}
		
	}

	
	@Override
	public Courier checkNum(String courierNum) {
		 Courier courier = courierDaoImpl.findByCourierNum(courierNum);
		 //System.out.println(courier);
		 return courier;
	}


	@Override
	public Page<Courier> findByPage(Specification<Courier> specification,Pageable pageable) {
		Page<Courier> page = courierDaoImpl.findAll(specification, pageable);
		return page;
	}


	@Override
	public void updateDelFlag(String flag,String[] ids) {
		Character flag1 = flag.charAt(0);
		for (String id : ids) {
			courierDaoImpl.updateDelFlag(flag1,Integer.valueOf(id));	
		}
		
	}

}

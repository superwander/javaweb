package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.exception.CourierException;

/**
 * 快递员接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-2 18:42:31
 */
public interface CourierService {

	/**
	 * 调用CourierDao层保存快递员信息的方法
	 * @throws CourierException 
	 */
	void save(Courier courier) throws CourierException;

	/**
	 * 调用CourierDao层方法验证快递员编号是否存在
	 */
	Courier checkNum(String courierNum);

	/**
	 * 调用CourierDao层方法分页查询Courier
	 * @param specification 
	 * @param pageable
	 * @return
	 */
	Page<Courier> findByPage(Specification<Courier> specification, Pageable pageable);

	/**
	 * 调用CourierDao层方法作废员工信息
	 * @param flag 
	 * @param ids
	 */
	void updateDelFlag(String flag, String[] ids);
}

package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.bos.domain.base.Courier;

/**
 * 快递员dao层接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-2 18:48:52
 */
@Repository
public interface CourierDao extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier>{

	/**
	 * 根据CourierNum查找对象
	 * 
	 * @param courierNum
	 * @return
	 */
	Courier findByCourierNum(String courierNum);

	/**
	 * 根据id作废员工信息
	 * @param integer 
	 * 
	 * @param valueOf
	 */
	@Query("update Courier set deltag=? where id = ?")
	@Modifying
	void updateDelFlag(Character id, Integer integer);

}

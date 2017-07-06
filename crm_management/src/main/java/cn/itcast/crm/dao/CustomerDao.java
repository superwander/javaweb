package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

/**
 * customerDao层接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-6 18:21:55
 */
public interface CustomerDao extends JpaRepository<Customer, Integer> {

	/**
	 * 查询所有未关联的客户
	 * 
	 * @return
	 */
	List<Customer> findByFixedAreaIdIsNull();

	/**
	 * 查询所有已关联的客户
	 * 
	 * @param fixedAreaId
	 * @return
	 */
	List<Customer> findByFixedAreaId(String fixedAreaId);

	/**
	 * 客户关联定区
	 * 
	 * @param id
	 * @param fixedAreaId
	 */
	@Query("update Customer set fixedAreaId = ?2 where id = ?1")
	@Modifying
	void updateFixedAreaId(Integer id, String fixedAreaId);

}

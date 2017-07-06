package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.exception.StandardException;

/**
 * StandardService接口
 * 
 * @author 王伟   
 * @version 1.0, 2017-7-1 09:00:43
 */
public interface StandardService {

	/**
	 * 调用StandanrdDao层添加收派标准方法
	 * 
	 * @param standard
	 */
	void save(Standard standard);

	/**
	 * 调用StandanrdDao层查询所有收派标准方法
	 * 
	 * @param standard
	 */
	List<Standard> findAll();

	/**
	 * 调用StandanrdDao层分页查询收派标准方法
	 * 
	 * @param standard
	 */
	Page<Standard> findPageData(Pageable pageable);
	
	public void deleteStanderds(String[] ids) throws StandardException;
}

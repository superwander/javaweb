package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.exception.FixedAreaException;

/**
 * 定区业务层接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-5 17:49:38
 */
public interface FixedAreaService {

	/**
	 * 调用FixedAreaDao层添加定区信息的方法
	 * 
	 * @param fixedArea
	 */
	void saveFixedArea(FixedArea fixedArea);

	/**
	 * 调用FixedAreaDao层条件分页查询定区信息的方法
	 * 
	 * @param specification
	 * @param pageable
	 * @return
	 */
	Page<FixedArea> findPageData(Specification<FixedArea> specification,
			Pageable pageable);

	/**
	 * 调用FixedAreaDao批量删除定区信息
	 * @param ids
	 * @throws FixedAreaException 
	 */
	void delFixedArea(String[] ids) throws FixedAreaException;
}

package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.exception.AreaException;

/**
 * 区域业务层接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-4 18:48:41
 */
public interface AreaService {

	/**
	 * 调用AreaDao层方法批量导入地区信息
	 * @param areas
	 */
	void batchImport(List<Area> areas);

	/**
	 * 调用AreaDao层方法按条件进行分页查询
	 * @param specification
	 * @param pageable
	 * @return
	 */
	Page<Area> findPageData(Specification<Area> specification,
			Pageable pageable);

	/**
	 * 调用AreaDao层方法保存区域信息
	 * @param model
	 */
	void saveArea(Area model);

	/**
	 * 调用AreaDao层方法批量删除区信息
	 * @param ids1
	 * @throws AreaException 
	 */
	void delArea(String[] ids1) throws AreaException;
}

package cn.itcast.bos.service.base.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.exception.FixedAreaException;
import cn.itcast.bos.service.base.FixedAreaService;

/**
 * FixedAreaService实现类
 * 
 * @author 王伟
 * @version 1.0, 2017-7-5 17:52:03
 */
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

	@Resource
	private FixedAreaDao fixedAreaDao;
	
	/**
	 * 保存定区信息
	 */
	@Override
	public void saveFixedArea(FixedArea fixedArea) {
		fixedAreaDao.save(fixedArea);
	}

	/**
	 * 按条件分页查询定区信息
	 */
	@Override
	public Page<FixedArea> findPageData(Specification<FixedArea> specification,
			Pageable pageable) {
		return fixedAreaDao.findAll(specification, pageable);
	}

	/**
	 * 批量删除定区信息
	 */
	@Override
	public void delFixedArea(String[] ids) throws FixedAreaException {
		
		for (String id : ids) {
			FixedArea fixedArea = fixedAreaDao.findOne(id);
			if(fixedArea==null){
				throw new FixedAreaException("删除失败，该id不存在");
			}
			fixedAreaDao.delete(fixedArea);
		}
	}

}

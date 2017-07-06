package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.exception.AreaException;
import cn.itcast.bos.service.base.AreaService;

/**
 * AreaService实现类
 * 
 * @author 王伟
 * @version 1.0, 2017-7-4 18:52:32
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDao areaDao;
	
	/**
	 * 批量导入区域数据
	 */
	@Override
	public void batchImport(List<Area> areas) {
		areaDao.save(areas);
	}

	/**
	 * 按条件进行分页查询
	 */
	@Override
	public Page<Area> findPageData(Specification<Area> specification,
			Pageable pageable) {
		Page<Area> page = areaDao.findAll(specification, pageable);
		return page;
	}

	/**
	 * 添加区域信息
	 */
	@Override
	public void saveArea(Area model) {
		areaDao.save(model);
	}

	/**
	 * 批量删除区域信息
	 */
	@Override
	public void delArea(String[] ids) throws AreaException {
		for (String id : ids) {
			Area area = areaDao.findOne(id);
			if(area==null){
				throw new AreaException("删除失败，该id不存在");
			}
			areaDao.delete(area);
		}
	}

}

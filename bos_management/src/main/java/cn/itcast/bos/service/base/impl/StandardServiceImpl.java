package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.StandardDao;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.exception.StandardException;
import cn.itcast.bos.service.base.StandardService;

/**
 * 收派标准接口实现类
 * 
 * @author 王伟
 * @version 1.0, 2017-7-1 09:03:14
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService{

	@Resource
	private StandardDao standardDao;
	
	@Override
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public List<Standard> findAll() {
		return standardDao.findAll();
	}

	@Override
	public Page<Standard> findPageData(Pageable pageable) {
		return standardDao.findAll(pageable);
	}


	@Override
	public void deleteStanderds(String[] ids) throws StandardException {
		for(String id : ids) {
			//Standard standard = standardDao.getOne(Integer.parseInt(id));
			Standard standard = standardDao.findOne(Integer.parseInt(id));
			if(standard == null) {
				throw new StandardException(id + "不村存在");
			}
			standardDao.delete(standard);
			
		}
	}
	
}

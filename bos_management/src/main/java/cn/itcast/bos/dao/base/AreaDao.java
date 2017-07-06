package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.Area;

/**
 * AreaDao接口
 * 
 * @author 王伟
 * @version 1.0, 2017-7-4 18:54:34
 */
public interface AreaDao
		extends JpaRepository<Area, String>, JpaSpecificationExecutor<Area> {

}

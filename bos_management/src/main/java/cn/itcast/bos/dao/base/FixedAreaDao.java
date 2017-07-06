package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.FixedArea;

/**
 * FixedAreaDao继承JpaRepository，JpaSpecificationExecutor
 * 
 * @author 王伟
 * @version 1.0, 2017-7-5 18:00:35
 */
public interface FixedAreaDao
		extends JpaRepository<FixedArea, String>, JpaSpecificationExecutor<FixedArea> {

}

package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.itcast.bos.domain.base.Standard;

/**
 * 收派标准dao层接口
 * 
 * @author 王伟   
 * @version 2017-7-1 09:09:09
 */
@Repository
public interface StandardDao extends JpaRepository<Standard,Integer>{

}

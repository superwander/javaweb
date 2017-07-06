package cn.itcast.bos.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.exception.CourierException;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.utils.SpecificationUtil;

/**
 * 快递员Action
 * 
 * @author 王伟
 * @version 1.0,
 */
@Controller
@Actions
@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{

	@Resource
	private CourierService courierServiceImpl;
	
	/**
	 * 将快递员信息传递到CourierService层
	 * 
	 * @return
	 */
	@Action(value="courier_save",results=@Result(name="success",type="redirect",location="/pages/base/courier.html"))
	//@Action(value="courier_save")
	public String save(){
		try {
			courierServiceImpl.save(model);
		} catch (CourierException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 验证员工编号是否存在，将参数传递到业务层
	 */
	@Action(value="checkNum")
	public void checkNum() {
		String courierNum = model.getCourierNum();
		Courier courier = courierServiceImpl.checkNum(courierNum);
			
		if(courier!=null){
			try {
				response.getWriter().print("false");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				System.out.println(false);
				response.getWriter().print("true");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 分页查询员工信息
	 * @return
	 */
	@Action(value="findCourierByPage",results=@Result(name="success",type="json"))
	public String findByPage(){
		
		Specification<Courier> specification = new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
			/*	//条件集合，用来存储条件
				List<Predicate> predicates = new ArrayList<>();
				
				//添加查询条件
				if(!StringUtils.isBlank(model.getCourierNum())){
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), model.getCourierNum());
					predicates.add(p1);
				}
				
				if(!StringUtils.isBlank(model.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class),"%"+ model.getCompany() +"%");
					predicates.add(p2);
				}
				
				if(!StringUtils.isBlank(model.getType())){
					Predicate p3 = cb.like(root.get("type").as(String.class),"%"+ model.getType() +"%");
					predicates.add(p3);
				}
				
				//多表查询
				Join<Courier, Standard> join = root.join("standard",JoinType.INNER);
				
				if(model.getStandard()!=null && model.getStandard().getName()!=null){
					Predicate p4 = cb.like(join.get("name").as(String.class),"%"+ model.getStandard().getName() +"%");
					predicates.add(p4);
				}*/
				//创建SpecificationUtil工具类
				SpecificationUtil<Courier> specificationUtil = new SpecificationUtil<>();
				//调用方法进行条件添加
				List<Predicate> predicates = specificationUtil.addPredicate(root, query, cb, model);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
			
		};
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Courier> pageData = courierServiceImpl.findByPage(specification,pageable);
		
		/*Map<String, Object> map = new HashMap<>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		
		ActionContext.getContext().getValueStack().push(map);*/
		pushToValueStack(pageData);
		return SUCCESS;
	}
	
	/** 要作废的ids */
	private String courierIds;
	
	/** 状态码  */
	private String flag;
	
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public void setCourierIds(String courierIds) {
		this.courierIds = courierIds;
	}

	/**
	 * 员工信息作废
	 */
	@Action(value="courierChangeFlag",results={@Result(name=SUCCESS,type="redirect",location="./pages/base/courier.html")})
	public String updateDelFlag(){
		String[] ids = courierIds.split(",");
		courierServiceImpl.updateDelFlag(flag,ids);
		return SUCCESS;
	}
}

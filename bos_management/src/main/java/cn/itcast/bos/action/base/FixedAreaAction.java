package cn.itcast.bos.action.base;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.exe.ResultInfo;
import cn.itcast.bos.exception.FixedAreaException;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.utils.SpecificationUtil;

/**
 * 定区Action
 * 
 * @author 王伟
 * @version 1.0, 2017-7-5 17:43:01
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@Actions
public class FixedAreaAction extends BaseAction<FixedArea>{
	
	@Resource
	private FixedAreaService fixedAreaServiceImpl;
	
	/**
	 * 接收参数并将参数传入业务层
	 * 
	 * @return
	 */
	@Action(value="addFixed_area",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String saveFixedArea(){
		fixedAreaServiceImpl.saveFixedArea(model);
		return SUCCESS;
	}
	
	@Action(value="findFixedAreaByPage",results={@Result(name="success",type="json")})
	public String findFixedAreaByPage(){
		
		//创建specification对象，添加查询条件
		Specification<FixedArea> specification = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				SpecificationUtil<FixedArea> specificationUtil = new SpecificationUtil<>();
				List<Predicate> predicates = specificationUtil.addPredicate(root, query, cb, model);
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		
		//创建分页对象
		Pageable pageable = new PageRequest(page-1, rows);
		Page<FixedArea> pageData = fixedAreaServiceImpl.findPageData(specification,pageable);
		pushToValueStack(pageData);
		return SUCCESS;
	}
	
	/** 要删除对象的ids */
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Action(value="delFixedArea",results={@Result(name="success",type="json")})
	public String delFixedArea(){
		String[] ids1 = ids.split(",");
		ResultInfo resultInfo = new ResultInfo("0","删除成功");
		try {
			fixedAreaServiceImpl.delFixedArea(ids1);
		} catch (FixedAreaException e) {
			resultInfo.setMsg(e.getMessage());
			resultInfo.setStatus("1");
			e.printStackTrace();
		}
		ActionContext.getContext().getValueStack().push(resultInfo);
		return SUCCESS;
	}

}

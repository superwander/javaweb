package cn.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.exe.ResultInfo;
import cn.itcast.bos.exception.StandardException;
import cn.itcast.bos.service.base.StandardService;

/**
 * 收派标准Action
 */
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
@Actions
public class StandardAction extends BaseAction<Standard>{

	@Resource
	private StandardService standardServiceImpl;

	/**
	 * 添加订单标准信息
	 * 
	 * @return
	 */
	@Action(value="standardSave",results={@Result(name="success",type="redirect",location="./pages/base/standard.html"),@Result(name="input",location="/error/error.jsp")})
	public String save(){
		standardServiceImpl.save(model);
		return SUCCESS;
	}
	
	/**
	 * 查询所有收派标准信息
	 */
	@Action(value="standardFindAll",results=@Result(name="success",type="json"))
	public String findAll(){
		List<Standard> standards = standardServiceImpl.findAll();
		ActionContext.getContext().getValueStack().push(standards);
		return SUCCESS;
	}
	
	/**
	 * 分页查询
	 * 
	 * @return
	 */
	@Action(value="pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> pageData = standardServiceImpl.findPageData(pageable);
		/*HashMap<String, Object> result = new HashMap<String ,Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows",pageData.getContent());
		ActionContext.getContext().getValueStack().push(result);*/
		//将pageData压入栈顶
		pushToValueStack(pageData);
		return SUCCESS;
	}
	
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="doDelete",results={@Result(name="success",type="json")})
	public String delete(){
		String[] standerdIds = ids.split(",");
		ResultInfo info = null;
		try {
			standardServiceImpl.deleteStanderds(standerdIds);
			info = new ResultInfo("1", "删除成功"); 
		} catch (StandardException e) {
			info = new ResultInfo("0", "删除失败"); 
			e.printStackTrace();
		}
		ActionContext.getContext().getValueStack().push(info);
		return SUCCESS;
	}
	
	
}

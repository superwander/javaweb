package cn.itcast.bos.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * BaseAction类
 * 
 * @author 王伟
 * @version 1.0, 2017-7-1 08:43:23
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
		
	protected T model;

	@Override
	public T getModel() {
		Class class1 = this.getClass();
		Type genericSuperclass = class1.getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Type actualTypeArgument = actualTypeArguments[0];
		Class type = (Class)actualTypeArgument;
		if(model==null){
			try {
				model = (T)type.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return model;
	}
	
	protected HttpServletRequest request = ServletActionContext.getRequest();
	
	protected HttpServletResponse response = ServletActionContext.getResponse();
	
	/** 当前页  */
	protected int page;
	
	/** 页面显示条数  */
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void pushToValueStack(Page<T> pageData){
		HashMap<String, Object> result = new HashMap<String ,Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows",pageData.getContent());
		ActionContext.getContext().getValueStack().push(result);
	}
}

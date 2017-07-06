package cn.itcast.bos.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;

/**
 * Specification封装条件的工具类
 * 
 * @param <T>
 */
public class SpecificationUtil<T>{
	
	/**
	 * 添加查询条件
	 * 
	 * @param root
	 * @param query
	 * @param cb
	 * @param object
	 * @return
	 */
	public List<Predicate> addPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder cb,Object object) {
		//获取class对象
		Class<? extends Object> class1 = object.getClass();
		//获取BeanInfo对象
		BeanInfo beanInfo=null;
		try {
			beanInfo = Introspector.getBeanInfo(class1);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		//获取目标类的所有属性
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		List<Predicate> predicates = new ArrayList<>();
		//遍历propertyDescriptors获取所有属性
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			//获取参数类型
			Class<?> paramType = propertyDescriptor.getPropertyType();
			//获取参数名
			String paramName = propertyDescriptor.getName();
			//获取该方法的返回值
			Object paramValue = null;
			try {
				paramValue = propertyDescriptor.getReadMethod().invoke(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			//判断paramValue是否为空
			if(paramValue!=null){
				//通过类加载器判断该对象是否为自定义类型对象，paramValue.getClass().getClassLoader()值为null，就是java已存在类型，不为空则为自定义类型
				ClassLoader classLoader = paramValue.getClass().getClassLoader();
				if(classLoader==null){
					if(paramType.toString().startsWith("class java.lang")){
						
						if(paramName.equals("class")){
							continue;
						}
						String value = String.valueOf(paramValue);
						if(StringUtils.isNotBlank(value)){
							Predicate p1 = cb.like( (Expression<String>) root.get(paramName).as(paramType),"%"+paramValue+"%");
							predicates.add(p1);
						}
					}
				}else {
					//自定义类型
					Join<Object, Object> join = root.join(paramName,JoinType.INNER);
					try {
						//添加自定义类型的条件
						joinRoot(join, query, cb, paramValue,predicates);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (IntrospectionException e) {
						e.printStackTrace();
					}
				}
			}
			//System.out.println(paramValue.getClass().getClassLoader());
			
		}
		return predicates;
		
	}
	
	/**
	 * 
	 * 
	 * @param join
	 * @param query
	 * @param cb
	 * @param object
	 * @param predicates
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public void joinRoot(Join<Object, Object> join, CriteriaQuery<?> query,
			CriteriaBuilder cb,Object object,List<Predicate> predicates) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		
		//获取class对象
		Class<? extends Object> class1 = object.getClass();
		//获取BeanInfo对象
		BeanInfo beanInfo = Introspector.getBeanInfo(class1);
		//获取目标类的所有属性
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		//遍历propertyDescriptors获取所有属性
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			//获取参数类型
			Class<?> paramType = propertyDescriptor.getPropertyType();
			String paramName = propertyDescriptor.getName();
			Object paramValue = propertyDescriptor.getReadMethod().invoke(object);
			//判断paramValue是否为空
			if(paramValue!=null){
				//通过类加载器判断该对象是否为自定义类型对象，paramValue.getClass().getClassLoader()值为null，就是java已存在类型，不为空则为自定义类型
				ClassLoader classLoader = paramValue.getClass().getClassLoader();
				if(classLoader==null){
					if(paramType.toString().startsWith("class java.lang")){
						//跳过class属性
						if(paramName.equals("class")){
							continue;
						}
						String value = String.valueOf(paramValue);
						if(StringUtils.isNotBlank(value)){
							Predicate p1 = cb.like( (Expression<String>) join.get(paramName).as(paramType),"%"+paramValue+"%");
							predicates.add(p1);
						}
					}
				}else {
					//自定义类型
					Join<Object, Object> joinRoot = join.join(paramName,JoinType.INNER);
					//递归调用
					joinRoot(joinRoot, query, cb, paramValue,predicates);
				}
			}
			//System.out.println(paramValue.getClass().getClassLoader());
			
		}
	}
}

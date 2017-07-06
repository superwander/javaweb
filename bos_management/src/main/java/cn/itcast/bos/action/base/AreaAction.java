package cn.itcast.bos.action.base;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.exe.ResultInfo;
import cn.itcast.bos.exception.AreaException;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.utils.SpecificationUtil;

/**
 * 区域Action
 * 
 * @author 王伟
 * @version 1.0, 2017-7-4 18:22:57
 */
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
@Actions
public class AreaAction extends BaseAction<Area>{

	@Resource
	private AreaService areaServiceImpl;
	
	/** 用户上传的文件对象  */
	private File file;

	public void setFile(File file) {
		this.file = file;
	}
	
	@Action(value="areaBatchImport")
	public void batchImport() {
		try{
			List<Area> areas = new ArrayList<Area>();
			//编写解析代码逻辑
			//基于.xls格式的解析
			//加载Excle文件
			Workbook hssfWorkbook=null;
			try {
				hssfWorkbook = WorkbookFactory.create(file);
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			}
			//create.getSheetAt(index)
			//HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
			//读取一个sheet
			Sheet sheetAt = hssfWorkbook.getSheetAt(0);
			for (Row row : sheetAt) {
				//跳过第一行
				if(row.getRowNum()==0){
					continue;
				}
				if(row.getCell(0)==null||row.getCell(0).getStringCellValue()==null){
					continue;
				}
				Area area = new Area();
				area.setId(row.getCell(0).getStringCellValue());
				area.setProvince(row.getCell(1).getStringCellValue());
				area.setCity(row.getCell(2).getStringCellValue());
				area.setDistrict(row.getCell(3).getStringCellValue());
				area.setPostcode(row.getCell(4).getStringCellValue());
				String city = area.getCity();
				String province = area.getProvince();
				String district = area.getDistrict();
				city = city.substring(0, city.length()-1);
				province = province.substring(0, province.length()-1);
				district = district.substring(0, district.length()-1);
				String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
				String shortcode = StringUtils.join(headByString);
				area.setShortcode(shortcode);
				String citycode= PinYin4jUtils.hanziToPinyin(city,"");
				area.setCitycode(citycode);
				areas.add(area);
				
			}
			//将数据传入业务层
			areaServiceImpl.batchImport(areas);
		
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	/**
	 * 接收参数，并将参数传入业务层
	 * 
	 * @return
	 */
	@Action(value="areaFindByPage",results={@Result(name="success",type="json")})
	public String findByPage(){
		
		Specification<Area> specification = new Specification<Area>() {
			//用来保存查询条件
			//List<Predicate> predicates = new ArrayList<>();
			@Override
			public Predicate toPredicate(Root<Area> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				SpecificationUtil<Area> specUtil = new SpecificationUtil<>();
				//调用工具类SpecificationUtil的addPredicate方法添加条件
				List<Predicate> predicates = specUtil.addPredicate(root, query, cb, model);
				return cb.and(predicates.toArray(new Predicate[0]));
			}};
			
		Pageable pageable = new PageRequest(page-1, rows);
		//获取pageData
		Page<Area> pageData = areaServiceImpl.findPageData(specification,pageable);
		//处理pageData数据并压入栈顶
		pushToValueStack(pageData);
		return SUCCESS;
	}
	
	/**
	 * 添加区域信息
	 * 
	 * @return
	 */
	@Action(value="saveArea",results={@Result(name=SUCCESS,type="redirect",location="./pages/base/area.html")})
	public String saveArea(){
		
		areaServiceImpl.saveArea(model);
		return SUCCESS;
	}
	
	/** 要删除对象的id信息  */
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 获取参数，并传入业务层
	 */
	@Action(value="delArea",results={@Result(name="success",type="json")})
	public String delArea(){
		String[] ids1 = ids.split(",");
		ResultInfo resultInfo = new ResultInfo("0","删除成功");
		try {
			areaServiceImpl.delArea(ids1);
		} catch (AreaException e) {
			resultInfo.setMsg(e.getMessage());
			resultInfo.setStatus("1");
			e.printStackTrace();
		}
		ActionContext.getContext().getValueStack().push(resultInfo);
		return SUCCESS;
	}
	
}

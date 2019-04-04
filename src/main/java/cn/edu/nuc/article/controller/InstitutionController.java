package cn.edu.nuc.article.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nuc.article.entity.Institution;
import cn.edu.nuc.article.service.InstitutionService;

/**
 * 机构Controller
 * @author 王凯
 *
 */
@Controller
@RequestMapping("/institution")
public class InstitutionController {

	/**
	 * 机构Service
	 */
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * 进入合并界面
	 * @return
	 */
	@RequestMapping("/toMerge")
	public String toMerge(Map<String, Object> map) {
		
		//1.找出所有下面没有用户的机构
		List<Institution> noUserInstitutions = institutionService.selectInstitutionNoUserUnder();
		
		//2.找出所有下面有用户并且没有被禁用的机构
		List<Institution> validInstitution = institutionService.selectInstitutionHasUserUnderAndValid();
		
		//3.保存查询结果
		map.put("noUserInstitutions", noUserInstitutions);
		map.put("validInstitution", validInstitution);
		
		return "institution/instutionMerge";
	}
	
	/**
	 * 机构合并
	 * @param map
	 * @param instid1
	 * @param instid2
	 * @return
	 */
	@RequestMapping("/merge")
	public String merge(Map<String, Object> map, Integer[] instid1, Integer instid2) {
		
		boolean result = false;
		try {
			result = institutionService.merge(instid1, instid2);
		} catch (Exception e) {
			System.out.println("合并过程中出错！");
			e.printStackTrace();
		}
		
		//判断操作是否成功
		if (result == true) {
			map.put("result", true);
			map.put("msg", "合并组织机构成功！");
		} else {
			map.put("result", true);
			map.put("msg", "合并组织机构出错，操作失败！");
		}
		
		return "forward:/institution/institutions";
	}
	
	/**
	 * 进入添加界面
	 * @return
	 */
	@RequestMapping("/toModify")
	public String toModify(Map<String, Object> map, Integer instid) {
		
		map.put("institution", institutionService.findById(instid));
		
		return "institution/institutionmodify";
	}
	
	/**
	 * 执行修改操作
	 * @param map
	 * @param institution
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/modify")
	public String modify(Map<String, Object> map, @Valid Institution institution, BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "修改组织机构[" + institution.getInstname() + "]失败！");
		} else {
			//功能名称查重
			boolean hasSame = institutionService.hasSameInstitution(
									institution.getInstid(), institution.getInstname());
			
			if (hasSame == false) { //功能名称不重复
				
				//保存功能信息，拿到添加操作的结果
				boolean result = institutionService.updateInstitution(institution);
				map.put("result", result);
				
				//根据操作结果生成提示信息
				if (result == true) {  //添加成功且无重复
					
					map.put("msg", "修改组织机构[" + institution.getInstname() + "]成功！");
				} else {
					
					map.put("msg", "修改组织机构[" + institution.getInstname() + "]失败！");
				}
				
			} else {
				map.put("result", false);
				map.put("msg", "组织机构名称[" + institution.getInstname() + "]重复，修改组织机构失败！");
			}
		}
			
		return "forward:/institution/institutions";
	}
	
	/**
	 * 进入添加界面
	 * @return
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		return "institution/institutionadd";
	}
	
	/**
	 * 执行添加操作
	 * @param map
	 * @param institution
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Map<String, Object> map, @Valid Institution institution, BindingResult bindingResult) {
		
		//检查校验是否出错
		if(bindingResult.hasErrors()){
			List<ObjectError> list = bindingResult.getAllErrors();
			ObjectError oe = list.get(0);
			
			//校验失败信息
			map.put("result", false);
			map.put("msg", oe.getDefaultMessage() + "添加组织机构[" + institution.getInstname() + "]失败！");
		} else {
			//功能名称查重
			boolean hasSame = institutionService.hasSameInstitution(null, institution.getInstname());
			
			if (hasSame == false) { //功能名称不重复
				
				//保存功能信息，拿到添加操作的结果
				boolean result = institutionService.addInstitution(institution);
				map.put("result", result);
				
				//根据操作结果生成提示信息
				if (result == true) {  //添加成功且无重复
					
					map.put("msg", "添加组织机构[" + institution.getInstname() + "]成功！");
				} else {
					
					map.put("msg", "添加组织机构[" + institution.getInstname() + "]失败！");
				}
				
			} else {
				map.put("result", false);
				map.put("msg", "组织机构名称[" + institution.getInstname() + "]重复，添加组织机构失败！");
			}
		}
			
		return "forward:/institution/institutions";
	}
	
	/**
	 * 模糊查询
	 * @param map 携带查询结果和参数
	 * @param pageNo 目标页
	 * @param pageCount 分页显示记录数
	 * @param keyword 搜索关键字
	 * @return
	 */
	@RequestMapping("/institutions")
	public String institutions(Map<String, Object> map,
			@RequestParam(value="pageNo", defaultValue="1", required=false) Integer pageNo,
			@RequestParam(value="pageCount", defaultValue="10", required=false) Integer pageCount,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pageNo, pageCount);
		
		// 分页查询得到结果集
		List<Institution> institutions;
		
		if (StringUtils.hasText(keyword)) {
			Institution institution = new Institution();
			institution.setInstname(keyword);
			institutions = institutionService.findByKeyword(institution);
		} else {
			institutions = institutionService.findByKeyword(null);
		}
		
		
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo<Institution> page = new PageInfo<Institution>(institutions, 5);
		
		//保存结果集带到页面显示
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "institution/institutionManage";
	}
}

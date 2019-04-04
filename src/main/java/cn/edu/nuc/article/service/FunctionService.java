package cn.edu.nuc.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.edu.nuc.article.dao.FunctionMapper;
import cn.edu.nuc.article.entity.Function;

/**
 * 功能业务逻辑
 * @author 王凯
 *
 */
@Service
public class FunctionService {
	
	/**
	 * 功能Mapper
	 */
	@Autowired
	private FunctionMapper functionMapper;
	
	/**
	 * 获得全部系统功能列表(功能管理本身使用，可以看到被禁用的功能)
	 * @return
	 */
	public List<Function> getFunctionList(Function function) {
		
		return functionMapper.selectByKeyWord(function);
	}
	
	/**
	 * 按id查询功能信息
	 * @return
	 */
	public Function getFunctionById(Integer funid) {
		
		return functionMapper.selectByPrimaryKey(funid);
	}
	
	/**
	 * 查出所有顶级功能
	 * @return
	 */
	public List<Function> getTopFunctions() {
		
		Function function = new Function();
		function.setFunpid(-1); //要求父功能为根节点“父功能”，加载出所有顶层功能
		
		return functionMapper.selectByKeyWord(function);
	}
	
	/**
	 * 判断是否有同名功能
	 * @param funname
	 * @return
	 */
	public boolean hasSameFunction(Integer funid, String funname) {
		
		//按名字模糊查询
		Function function = new Function();
		function.setFunname(funname);
		if (funid != null) {
			function.setFunid(funid);
		}
		List<Function> functions = functionMapper.selectByFunname(function);
		
		//只要有一样的就是重复的
		if (functions.size() > 0) {
			return true;
		}
		
		//否认将认定为不重复
		return false;
	}
	
	/**
	 * 新增功能信息
	 * @return
	 */
	@Transactional
	public boolean addFunction(Function function) {
		
		return functionMapper.insertSelective(function) > 0;
	}
	
	/**
	 * 修改功能信息
	 * @param function
	 * @return
	 */
	@Transactional
	public boolean modifyFunction(Function function) {
		
		return functionMapper.updateByPrimaryKeySelective(function) > 0;
	}

}

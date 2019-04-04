package cn.edu.nuc.test.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.FunctionMapper;
import cn.edu.nuc.article.entity.Function;

/**
 * 功能Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class FunctionMapperTest {
	
	/**
	 * 功能Mapper
	 */
	@Autowired
	public FunctionMapper functionMapper;
	
	/**
	 * 修改操作
	 */
//	@Test
//	public void testUpdate() {
//		
//		Function function = functionMapper.selectByPrimaryKey(20);
//		function.setFunname("测试1");
//		function.setFunstate(1);
//		
//		functionMapper.updateByPrimaryKeySelective(function);
//	}
	
	/**
	 * 测试插入功能，插入记录后主键会自动填回
	 */
//	@Test
//	public void testInsertAndDelete() {
//		
//		Function function = new Function();
//		function.setFunname("测试");
//		function.setFunstate(1);
//		function.setFunpid(-1);
//		
//		int result = functionMapper.insertSelective(function);
//		System.out.println("-------------------------");
//		System.out.println(result + "行受影响!");
//		System.out.println(functionMapper.selectByPrimaryKey(function.getFunid()));
//		
//		assertFalse("插入失败", result < 1);
//		
//		System.out.println("--------------------------");
//
//	}
	
	/**
	 * 测试列表查询
	 */
	@Test
	public void testSelectFunctionList() {
		
		List<Function> functions = functionMapper.selectByKeyWord(null);
		
		System.out.println("---------------------------------------");
		for (Function function : functions) {
			System.out.println(function);
		}
		System.out.println("---------------------------------------");
		
		//不算测试数据，无查询条件情况下应该是19条记录
		assertFalse("插入失败", functions.size() < 19);

	}
	
	/**
	 * 测试按id查询记录：功能树的根节点名称固定为"父功能"，通过提前置入方式进入数据库
	 */
	@Test
	public void testSelectFunctionById() {
		Function function = functionMapper.selectByPrimaryKey(-1);
		System.out.println("------------------------------");
		System.out.println(function);
		System.out.println("------------------------------");
		assertEquals("功能模块按id查询单元测试失败！", function.getFunname(), "父功能");
	}

}

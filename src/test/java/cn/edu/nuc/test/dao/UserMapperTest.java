package cn.edu.nuc.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.UserMapper;
import cn.edu.nuc.article.entity.User;

/**
 * 用户Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class UserMapperTest {

	/**
	 * 用户Mapper
	 */
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 测试修改操作
	 */
//	@Test
//	public void testModify() {
//		
//		User user = userMapper.selectByPrimaryKey(3);
//		user.setUserstate(0);
//
//		
//		int result = userMapper.updateByPrimaryKeySelective(user);
//		
//		if (result > 0) {
//			System.out.println("修改成功！");
//		} else {
//			System.out.println("修改失败！");
//		}
//		
//	}
	
	/**
	 * 测试添加操作
	 */
//	@Test
//	public void testAdd() {
//		
//		User user = new User();
//		user.setLoginname("wangwu");
//		user.setPassword("123");
//		user.setJob("审核组长");
//		user.setEmail("wangwu@nuc.edu.cn");
//		user.setUsertruename("王五");
//		user.setInstId(1);
//		user.setUserstate(1);
//		user.setPhone("13000000000");
//		user.setRoleId(2);
//		
//		int result = userMapper.insertSelective(user);
//		
//		if (result > 0) {
//			System.out.println("插入成功！");
//		} else {
//			System.out.println("插入失败！");
//		}
//		
//	}
	
	/**
	 * 测试登录名是否重复
	 */
	@Test
	public void testLoginname() {
		
		List<User> users = userMapper.loginnameValidate(null, "zhangsan");
		System.out.println(users.size() == 0);  //false
		
		assertEquals("登录名检查出错", false , users.size() == 0);
		
		System.out.println("----------------------------");
		
		users = userMapper.loginnameValidate(1, "zhangsan");
		System.out.println(users.size() == 0); //true
		
		assertEquals("登录名检查出错", true , users.size() == 0);
		
	}
	
	/**
	 * 测试登录验证
	 */
	@Test
	public void testLoginValidate() {
		
		User user = userMapper.loginValidate("zhangsan", "123");
		System.out.println(user);
		
		user = userMapper.loginValidate("lisi", "123");
		System.out.println(user);
	}
	
	/**
	 * 测试按主键查询
	 */
	@Test
	public void testfindById() {
		
		User user = userMapper.selectByPrimaryKey(1);
		
		System.out.println("-----------------------------------------");
		System.out.println(user);
		System.out.println("-----------------------------------------");
	}
	
	/**
	 * 测试模糊查询
	 */
	@Test
	public void testList() {
		
		List<User> users = userMapper.selectByKeyWord(null);
		System.out.println("----------------------------------------");
		for (User user : users) {
			System.out.println(user);
		}
		System.out.println("----------------------------------------");
		
		User user = new User();
		user.setUserstate(1);
		
		users = userMapper.selectByKeyWord(user);
		
		for (User user1 : users) {
			System.out.println(user1);
		}
		
		System.out.println("----------------------------------------");
	}
}

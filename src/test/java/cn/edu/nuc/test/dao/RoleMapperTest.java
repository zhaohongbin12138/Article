package cn.edu.nuc.test.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import cn.edu.nuc.article.dao.RoleMapper;
import cn.edu.nuc.article.entity.Role;

/**
 * 角色Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml",
		"classpath:spring-activiti.xml"})
public class RoleMapperTest {
	
	/**
	 * 角色DAO
	 */
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 测试同名检测
	 */
	@Test
	public void testHasSame() {
		
		List<Role> roles = roleMapper.hasSameRole(null, "系统管理员");
		if (roles.size() > 0) {
			System.out.println("有重复");//显示这个
		} else {
			System.out.println("无重复");
		}
		
		System.out.println("-----------------------------------");
		
		roles = roleMapper.hasSameRole(1, "系统管理员");
		if (roles.size() > 0) {
			System.out.println("有重复");
		} else {
			System.out.println("无重复");//显示这个
		}
	}
	
	/**
	 * 测试权限
	 */
//	@Test
//	public void testRoleRight() {
//		
//		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
//		
//		RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
//		
//		roleMapper.deleteOldRoleRights(1);
//		
//		roleMapper.insertNewRoleRightInfo(1, -1);
//		for (int i = 1; i < 20; i++) {
//			roleMapper.insertNewRoleRightInfo(1, i);
//		}
//		
//		sqlSession.commit();
//		sqlSession.close();
//		
//	}
	
	/**
	 * 测试修改操作
	 */
//	@Test
//	public void testUpdate() {
//		
//		Role role = roleMapper.selectByPrimaryKey(3);
//		role.setRolestate(0);
//		
//		int column = roleMapper.updateByPrimaryKeySelective(role);
//		System.out.println("受影响行数" + column);
//		assertEquals("修改失败！", 1, column);
//	}
	
	/**
	 * 测试添加操作
	 */
//	@Test
//	public void testInsert() {
//		
//		Role role = new Role();
//		role.setRolename("普通工作人员");
//		role.setRoledesc("只有发文和看公文的权限");
//		role.setRolestate(1);
//		
//		int column = roleMapper.insertSelective(role);
//		System.out.println("受影响行数" + column);
//		assertEquals("插入失败！", 1, column);
//	}
	
	/**
	 * 测试模糊查询
	 */
	@Test
	public void testFindByIdCascade() {
		
		//查出来的结果系统管理员是没有级联功能信息的
		System.out.println("---------------------------");
		Role role = roleMapper.selectByPrimaryKeyCascade(1);
		System.out.println(role);
		System.out.println("---------------------------");
	}
	
	/**
	 * 测试模糊查询
	 */
	@Test
	public void testFindById() {
		//查出来的结果系统管理员是没有级联功能信息的
		Role role = roleMapper.selectByPrimaryKey(1);
		System.out.println(role);
	}
	
	/**
	 * 测试模糊查询
	 */
	@Test
	public void testList() {
		
		//查出来的结果是没有级联功能信息的
		List<Role> roles = roleMapper.selectByKeyword(null);
		System.out.println("--------------------------------------");
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.println("--------------------------------------");
		
		Role role1 = new Role();
		role1.setRolename("系统管理员");
		roles = roleMapper.selectByKeyword(role1);
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.println("--------------------------------------");
	}
	
	/**
	 * 测试级联模糊查询
	 */
	@Test
	public void testListCascade() {
		
		List<Role> roles = roleMapper.selectByKeywordCascade(null);
		System.out.println("--------------------------------------");
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.println("--------------------------------------");
		
		Role role1 = new Role();
		role1.setRolename("系统管理员");
		roles = roleMapper.selectByKeywordCascade(role1);
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.println("--------------------------------------");
	}
}

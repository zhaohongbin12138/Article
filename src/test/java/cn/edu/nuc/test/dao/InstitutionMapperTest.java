package cn.edu.nuc.test.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.InstitutionMapper;
import cn.edu.nuc.article.entity.Institution;

/**
 * 机构Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class InstitutionMapperTest {
	
	/**
	 * 机构DAO
	 */
	@Autowired
	private InstitutionMapper institutionMapper;
	
//	/**
//	 * 测试检查机构下面是否有人的功能
//	 */
//	@Test
//	public void testValidate() {
//		
//		long count = institutionMapper.isInstitutionHasUser(1);
//		if (count > 0) {//应该走if分支
//			System.out.println("该机构下面有用户，不能合并！");
//		} else {
//			System.out.println("该机构下面没有用户，可以合并！");
//		}
//		
//		count = institutionMapper.isInstitutionHasUser(4);
//		if (count > 0) {
//			System.out.println("该机构下面有用户，不能合并！");
//		} else {//应该走else分支
//			System.out.println("该机构下面没有用户，可以合并！");
//		}
//	}
//	
//	/**
//	 * 测试修改操作
//	 */
//	@Test
//	public void testupdate() {
//		
//		Institution institution = institutionMapper.selectByPrimaryKey(4);
//		institution.setInststate(0);
//		
//		int result = institutionMapper.updateByPrimaryKeySelective(institution);
//		System.out.println("受影响行数:" + result);
//		assertEquals("修改失败！", 1, result);
//	}
//	
//	/**
//	 * 测试添加操作
//	 */
//	@Test
//	public void testadd(){
//		
//		Institution institution = new Institution();
//		institution.setInstname("测试");
//		institution.setInstaddr("测试地址");
//		institution.setPostcode("000000");
//		institution.setInststate(1);
//		
//		int result = institutionMapper.insertSelective(institution);
//		System.out.println("受影响行数:" + result);
//		assertEquals("插入失败！", 1, result);
//	}
	
	/**
	 * 测试按id查询
	 */
	@Test
	public void testId() {
		
		System.out.println("-----------------------------------------");
		System.out.println(institutionMapper.selectByPrimaryKey(2));
		System.out.println("-----------------------------------------");
	}
	
	/**
	 * 测试是否重名
	 */
	@Test
	public void testHasSame() {
		
		System.out.println("--------------------------------------");
		if (institutionMapper.hasSameInstitution(null, "太原理工大学").size() == 0) {
			System.out.println("太原理工大学没重名");//√
		} else {
			System.out.println("太原理工大学重名");
		}
		assertEquals("重名测试不通过", true, institutionMapper.hasSameInstitution(null, "太原理工大学").size() == 0);
		System.out.println("--------------------------------------");

		if (institutionMapper.hasSameInstitution(2, "中北大学").size() == 0) {
			System.out.println("中北大学没重名");
		} else {
			System.out.println("中北大学重名");//√
		}
		assertEquals("重名测试不通过", false, institutionMapper.hasSameInstitution(2, "中北大学").size() == 0);
		System.out.println("--------------------------------------");
	}
	
	/**
	 * 测试模糊查询
	 */
	@Test
	public void testList() {
		
		Institution institution = new Institution();
		institution.setInstname("中");
		List<Institution> institutions = institutionMapper.selectByKeyWord(institution);
		System.out.println("----------------------------");
		for (Institution institution2 : institutions) {
			System.out.println(institution2);
		}
		System.out.println("---------------------------");
		
	}

	
}

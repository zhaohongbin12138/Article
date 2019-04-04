package cn.edu.nuc.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.ReceiveMapper;

/**
 * 公文接收Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class ReceiveMapperTest {

	/**
	 * 公文接收Mapper
	 */
	@Autowired
	private ReceiveMapper receiveMapper;
	
	/**
	 * 测试删除操作
	 */
//	@Test
//	public void testDelete() {
//		
//		int count = receiveMapper.deleteByArticleId(2);
//		if (count > 0) {
//			System.out.println("删除成功！");
//		} else {
//			System.out.println("删除失败！");
//		}
//	}
	
	
	/**
	 * 测试添加公文接收信息
	 */
//	@Test
//	public void testAdd() {
//		
//		Receive receive = new Receive();
//		receive.setReceiverId(2);
//		receive.setArticleId(3);
//		
//		int count = receiveMapper.insertSelective(receive);
//		
//		if (count > 0) {
//			System.out.println("添加成功");
//		} else {
//			System.out.println("添加失败");
//		}
//		
//	}
}

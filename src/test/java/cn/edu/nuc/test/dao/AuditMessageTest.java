package cn.edu.nuc.test.dao;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.AuditMessageMapper;
import cn.edu.nuc.article.entity.AuditMessage;

/**
 * 审核信息Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class AuditMessageTest {

	/**
	 * 审核信息Mapper
	 */
	@Autowired
	private AuditMessageMapper auditMessageMapper;
	
	/**
	 * 测试添加审核信息
	 */
//	@Test
//	public void testSave() {
//		
//		AuditMessage auditMessage = new AuditMessage();
//		auditMessage.setAuditdate(new Date());
//		auditMessage.setAuditmessage("可以了！");
//		auditMessage.setAuditresult(1);
//		auditMessage.setArticleId(1);
//		
//		int column = auditMessageMapper.insertSelective(auditMessage);
//		
//		if (column > 0) {
//			System.out.println("添加成功！");
//		} else {
//			System.out.println("添加失败！");
//		}
//	}
	
}

package cn.edu.nuc.test.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.AttachmentMapper;
import cn.edu.nuc.article.entity.Attachment;

/**
 * 附件Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class AttachmentMapperTest {

	/**
	 * 附件Mapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;
	
//	/**
//	 * 测试删除操作
//	 */
//	@Test
//	public void testDelete() {
//		
//		int count = attachmentMapper.deleteByPrimaryKey(4);
//		if (count > 0) {
//			System.out.println("删除成功！");
//		} else {
//			System.out.println("删除失败！");
//		}
//	}
	
	/**
	 * 测试添加操作
	 */
//	@Test
//	public void testAdd() {
//		
//		Attachment attachment = new Attachment();
//		attachment.setArticleId(1);
//		attachment.setAttachtype(1);
//		attachment.setFilesize(1000);
//		attachment.setFilename("测试.doc");
//		attachment.setUploadtime(new Date());
//		attachment.setFileid("aaaaaaaa");
//		attachment.setMimetype("application/msword");
//		
//		for (int i = 0; i < 20; i++) {
//			int count = attachmentMapper.insertSelective(attachment);
//			if (count > 0) {
//				System.out.println("保存成功！");
//			} else {
//				System.out.println("保存失败！");
//			}
//		}
//	}
	
	/**
	 * 测试级联模糊查询
	 */
	@Test
	public void testList() {
		
		List<Attachment> attachments = null;
		attachments = attachmentMapper.selectByKeyword(null);
		
		System.out.println("------------------------------------");
		
		for (Attachment attachment : attachments) {
			System.out.println(attachment);
		}
		
		System.out.println("------------------------------------");
		
		Attachment attachment = new Attachment();
		attachment.setFilename("研究生");
		
		attachments = attachmentMapper.selectByKeyword(attachment);
		
		for (Attachment attachment1 : attachments) {
			System.out.println(attachment1);
		}
		
		System.out.println("------------------------------------");
		
	}

}

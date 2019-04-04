package cn.edu.nuc.test.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.ArticleMapper;
import cn.edu.nuc.article.entity.Article;

/**
 * 公文Mapper单元测试
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class ArticleMapperTest {
	
	/**
	 * 公文Mapper
	 */
	@Autowired
	private ArticleMapper articleMapper;
	
	/**
	 * 测试公文标题检查
	 */
	@Test
	public void testTitleValidate() {
		
		long count = articleMapper.validateTitle("单元测试：修改1", null);
		
		if (count > 0) {
			System.out.println("有重复");
		} else {
			System.out.println("无重复");
		}
		
	}
	
	/**
	 * 测试删除
	 */
//	@Test
//	public void testDelete() {
//		
//		int column = articleMapper.deleteById(5);
//		
//		if (column > 0) {
//			System.out.println("删除成功！");
//		} else {
//			System.out.println("删除失败！");
//		}
//		
//	}
	
	/**
	 * 测试修改
	 */
//	@Test
//	public void testUpdate() {
//		Article article = articleMapper.selectOne(4);
//		article.setTitle("单元测试：修改");
//		
//		int column = articleMapper.updateByPrimaryKeySelective(article);
//		
//		if (column > 0) {
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
//	public void testSave() {
//		
//		Article article = new Article();
//		article.setTitle("测试");
//		article.setCopywriterId(1);
//		article.setAuditorId(2);
//		article.setInstId(1);
//		article.setProcessinstanceId("proinstancekey");
//		article.setPublishtime(new Date());
//		article.setArticlestate(1);
//		
//		int column = articleMapper.insertSelective(article);
//		
//		if (column > 0) {
//			System.out.println("插入成功！" + article);
//		} else {
//			System.out.println("插入失败！");
//		}
//	}
	
	/**
	 * 测试接收数量
	 */
	@Test
	public void testReceiveCount() {
		
		System.out.println(articleMapper.selectMyCountReceiver(1));
		
	}
	
	/**
	 * 测试加载与我相关列表
	 */
	@Test
	public void testMyList() {
		
		Article param = new Article();
		param.setUserId(1);
		
		List<Article> mylist = articleMapper.selectMyReceiveList(param);
		System.out.println("------------------------------------");
		
		for (Article article : mylist) {
			System.out.println(article);
		}
		
		System.out.println("------------------------------------");
	}
	
	/**
	 * 测试按id加载
	 */
	@Test
	public void testFindById() {
		
		System.out.println(articleMapper.selectOne(1,1));
	}
	
	/**
	 * 测试按列表加载
	 */
	@Test
	public void testList() {
		
		List<Article> articles = articleMapper.selectListAll(null);
		System.out.println("------------------------------------");
		
		for (Article article : articles) {
			System.out.println(article);
		}
		
		System.out.println("------------------------------------");
	}

}

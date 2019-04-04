package cn.edu.nuc.test.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.dao.LogMapper;
import cn.edu.nuc.article.entity.Log;

/**
 * 测试日志功能是否正常
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class LogMapperTest {
	
	/**
	 * 日志Mapper
	 */
	@Autowired
	private LogMapper logMapper;
	
	@Test
	public void testList() {
		
		List<Log> logs = logMapper.selectByKeyword(null);
		
		System.out.println("---------------------------");
		for (Log log : logs) {
			System.out.println(log);
		}
		System.out.println("---------------------------");
		
		Log log = new Log();
		log.setOptname("测");
		System.out.println(log);
		logs = logMapper.selectByKeyword(log);
		
		System.out.println("---------------------------");
		for (Log log1 : logs) {
			System.out.println(log1);
		}
		System.out.println("---------------------------");
	}

	/**
	 * 测试添加功能是否正常
	 */
	@Test
	public void testSave() {
		
		Log log = new Log();
		log.setOperatorId(1);
		log.setIpaddress("127.0.0.1");
		log.setOptname("测试");
		log.setBussinessId(1);
		
		System.out.println(log);
		
		int count = logMapper.insertSelective(log);
		
		System.out.println("------------------" + count);
	}

}

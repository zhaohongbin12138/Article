package cn.edu.nuc.test.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.service.FileService;

/**
 * 测试文件服务
 * 
 * @author 王凯
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class TestFileService {

	@Autowired
	private FileService fileService;
	
	/**
	 * 测试文件保存
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSave() throws Exception {
		
		FileInputStream is = new FileInputStream("d:\\dev\\24 基于Activiti的OA办公系统 启动请假流程.mp4");
		String fileId = fileService.save(is);
		System.out.println(fileId);
		is.close();
	}

	/**
	 * 测试文件获取
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		//660dc380-d6e5-4fef-97e6-d65e06fb85fa
		InputStream is = fileService.getByFileId("4fc5bcfa-8cc7-4883-b2b1-791dd3ac94fa");

		if (is != null) {
			FileOutputStream os = new FileOutputStream(new File("d:\\24 基于Activiti的OA办公系统 启动请假流程.mp4"));
			byte[] data = new byte[1024];
			int len = 0;
			while ((len = is.read(data)) != -1) {
				os.write(data, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		}

	}
	
	/**
	 * 测试删除操作
	 */
	@Test
	public void testDelete() {
		fileService.delete("4fc5bcfa-8cc7-4883-b2b1-791dd3ac94fa");
	}

}

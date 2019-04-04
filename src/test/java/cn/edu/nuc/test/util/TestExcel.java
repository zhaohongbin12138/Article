package cn.edu.nuc.test.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.nuc.article.util.ExcelTools;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml","classpath:spring-activiti.xml"})
public class TestExcel {

	@Test
	public void testExcel() {
		try {

            // 对读取Excel表格标题测试
            InputStream is = TestExcel.class.getClassLoader().getResourceAsStream("excel/用户模板.xls");
            ExcelTools excelReader = new ExcelTools();
            String[] title = excelReader.readExcelTitle(is);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }

            // 对读取Excel表格内容测试
            InputStream is2 = TestExcel.class.getClassLoader().getResourceAsStream("excel/用户模板.xls");
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            System.out.println("获得Excel表格的内容:");
            for (int i = 1; i <= map.size(); i++) {
                System.out.println(map.get(i));
                String notdeal = (String)map.get(i);
                String[] data = notdeal.split("-");
                for (String string : data) {
					System.out.print(string);
				}
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
	}
	
}

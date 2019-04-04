package cn.edu.nuc.article.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 生成验证码工具
 * @author 王凯
 *
 */
public final class CodeUtil {

	/**
	 * 验证码字体
	 */
	private static Font[] codeFont = {
			new Font("Times New Roman", Font.PLAIN,30),
			new Font("Times New Roman", Font.PLAIN,30),
			new Font("Times New Roman", Font.PLAIN,30),
			new Font("Times New Roman", Font.PLAIN,30)
	};
	
	/**
	 * 验证码字符颜色
	 */
	private static Color[] color = {
		Color.BLACK,  Color.RED,  Color.DARK_GRAY,  Color.BLUE	
	};

	/**
	 * 生成验证码的字符(A-Z,a-z,0-9),验证码形成时将会从这里选取字符
	 */
	private static final String IMAGE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijkmnpqrstuvwxyz23456789";
	
	/**
	 * 生成验证码图片的宽度
	 */
	private static final Integer IMAGE_WIDTH = 160;
	
	/**
	 * 生成验证码图片的高度
	 */
	private static final Integer IMAGE_HEIGHT = 40;
	
	/**
	* 功能函数：绘制验证码的一个字符
	* @param graphics 绘图对象（画笔）
	* @param i 验证码字符序号
	*/
	private static String drawCode(Graphics graphics, int i){
		Random random = new Random();
		//产生随机切割序号 0-61.9999
		Integer j = random.nextInt((IMAGE_CHAR.length()));
		//切割随机数
		String number = IMAGE_CHAR.substring(j, j+1);
		
		//设置验证码字符的字体和颜色
		graphics.setFont(codeFont[i]);
		graphics.setColor(color[i]);


		//绘制验证码到图片X、Y（每个字体x每步进13的倍数，y不变，大小6*6）
		//number是要绘制字符的值，
		//6 + i * 13 是要绘制的x轴坐标，x轴递增13实现字符之间间距为13
		//16是指字符绘制的y轴坐标
		graphics.drawString(number, 22 + i * 26, 28);

		return number;
	}

	/**
	* 产生随机背景色
	* @param fc 颜色基调
	* @param bc 颜色边界
	* @return 随机产生的背景色
	*/
	private static Color getRandColor(int fc,int bc){
		//随机对象
		Random random = new Random();
		//随机初始数值不得大于255
		if(fc > 255)
			fc = 255;
		//随机初始数值不得大于255
		if(bc > 255)
			bc = 255;
		//产生随机红蓝绿色调
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r,g,b);
	}

	/**
	* 功能函数：绘制干扰线
	* @param graphics 绘图对象（画笔）
	* @param lineNumber 干扰线条数
	*/
	private static void drawNoise(Graphics graphics, int lineNumber){
		//干扰线颜色
		graphics.setColor(getRandColor(160, 200));
		
		for (int i = 0; i < lineNumber; i++) {
			//线的起始X轴（只在80，20的范围内随机，由于从0开始，所以要加1）
			int pointX1 = 1 + (int) (Math.random() * 160);
			//Math类的random()方法生成0.0-1.0之间的随机数
			//线的起始Y轴（只在80，20的范围内随机，由于从0开始，所以要加1）
			int pointY1 = 1 + (int) (Math.random() * 40);
			//线的终止X轴（只在80，20的范围内随机，由于从0开始，所以要加1）
			int pointX2 = 1 + (int) (Math.random() * 160);
			//线的终止Y轴（只在80，20的范围内随机，由于从0开始，所以要加1）
			int pointY2 = 1 + (int) (Math.random() * 40);
			
			graphics.drawLine(pointX1, pointY1, pointX2, pointY2);
		}
	}

	/**
	* 覆写doGet方法，完成验证码的生成
	*/
	public static void drawCode(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/gif");
		
		//不设置缓存，页面不使用缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
			
		//创建一个图像，验证码显示图片大小
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, 
				BufferedImage.TYPE_INT_RGB);
		//BufferedImage.TYPE_INT_RGB表示图片中的每一个像素都可以由红绿蓝三种色调
		
		//获取图片绘制对象(画笔)
		Graphics g = image.getGraphics();
		
		//绘制图片背景颜色
		g.setColor(getRandColor(200, 250));
		
		//绘制背景图片
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		//前两个参数是指图形区域开始（左上角）的横纵坐标
		//后两个参数是指图形区域的宽度和高度
		
		String codeNumbers = "";
		//循环产生4位随机数
		for (int i = 0; i < 4; i++) {
			codeNumbers = codeNumbers + drawCode(g, i);
		}
		
		//添加干扰线
		drawNoise(g, 12);
		
		//将验证码内容保存进session中，用于验证用户输入是否正确时使用
		HttpSession session = request.getSession(true);
		session.removeAttribute("code");
		session.setAttribute("code", codeNumbers);
		
		//利用ImagieIO类的write方法对图像进行编码
		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "GIF", sos);
		sos.close();
	}

}

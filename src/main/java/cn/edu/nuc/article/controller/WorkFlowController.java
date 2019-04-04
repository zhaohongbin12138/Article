package cn.edu.nuc.article.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nuc.article.service.WorkFlowDefinationService;

/**
 * 管理工作流的Controller
 * @author 王凯
 *
 */
@Controller
@RequestMapping("/workflow")
public class WorkFlowController {
	
	/**
	 * 引入工作流定义的Service
	 */
	@Autowired
	private WorkFlowDefinationService workFlowDefinationService;
	
	/**
	 * 分页模糊查询流程部署信息
	 * @return
	 */
	@RequestMapping("/definations")
	public String definations(Map<String, Object> map,
			@RequestParam(value="pageNo", defaultValue="1", required=false) Integer pageNo,
			@RequestParam(value="pageCount", defaultValue="10", required=false) Integer pageCount,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pageNo, pageCount);
		
		// 分页查询得到结果集
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo<ProcessDefinition> page = workFlowDefinationService.findPorcessDefinationByPage(pageNo, pageCount, keyword);
		
		//保存结果集带到页面显示
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "workflow/definationManage";
	}
	
	/**
	 * 进入显示流程图界面
	 * @return
	 */
	@RequestMapping("/showDefinations")
	public String showDefinations(Map<String, Object> map, 
			String deploymentId, String imageName) {
		
		map.put("deploymentId", deploymentId);
		map.put("imageName", imageName);
		
		return "workflow/definationshow";
	}
	
	/**
	 * 显示流程图
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/showPicture")
	public void showPicture(HttpServletResponse response ,
			String deploymentId, String imageName) throws Exception {
		
		//1：获取页面传递的部署对象ID和资源图片名称(参数已经注入)
		
		//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workFlowDefinationService.findImageInputStream(deploymentId,imageName);
		
		//3：从response对象获取输出流
		OutputStream out = response.getOutputStream();
		
		//4：将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
	}
	
	/**
	 * 分页模糊查询流程部署信息
	 * @return
	 */
	@RequestMapping("/deploys")
	public String deploys(Map<String, Object> map,
			@RequestParam(value="pageNo", defaultValue="1", required=false) Integer pageNo,
			@RequestParam(value="pageCount", defaultValue="10", required=false) Integer pageCount,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pageNo, pageCount);
		
		// 分页查询得到结果集
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo<Deployment> page = workFlowDefinationService.findDeploymentByPage(
											pageNo, pageCount, keyword);
		
		//保存结果集带到页面显示
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "workflow/deployManage";
	}
	
	/**
	 * 进入部署新流程界面
	 * @return
	 */
	@RequestMapping("/toDeploy")
	public String toDeploy() {
		
		return "workflow/deployprocess";
	}
	
	/**
	 * 部署新的流程
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/deployNewProcess")
	public String deployNewProcess(Map<String, Object> map ,
			@RequestParam("file") MultipartFile file){
		
		try {
			
			//转换为压缩流
			ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream());
			
			//部署流程
			workFlowDefinationService.deployNewProcess(zipInputStream, file.getOriginalFilename());
			
			map.put("result", true);
			map.put("msg", "部署新流程成功！");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);
			map.put("msg", "部署新流程失败！");
		}
		return "forward:/workflow/deploys";
	}
	
	/**
	 * 删除部署信息
	 * @return
	 */
	@RequestMapping("/removeDeploy")
	public String removeDeploy(Map<String, Object> map, String deploymentId) {
		
		try {
			//删除部署的流程
			workFlowDefinationService.deleteProcessDefinitionByDeploymentId(deploymentId);
			
			map.put("result", true);
			map.put("msg", "移除部署成功！");
		} catch (Exception e) {
			e.printStackTrace();
			
			map.put("result", false);
			map.put("msg", "移除部署失败！");
		}
		
		return "forward:/workflow/deploys";
	}

}

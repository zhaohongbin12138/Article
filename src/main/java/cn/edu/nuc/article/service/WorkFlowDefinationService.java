package cn.edu.nuc.article.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * 工作流部署和定义业务逻辑
 * @author 王凯
 *
 */
@Service
public class WorkFlowDefinationService{
	
	/**
	 * Activiti的仓库Service
	 */
	@Autowired
	private RepositoryService repositoryService;
	
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	
	/**
	 * 部署新流程
	 * @throws Exception 
	 */
	public void deployNewProcess(ZipInputStream inputStream, String deployName) 
				throws Exception {
		
		repositoryService.createDeployment()//创建部署对象
			.name(deployName)//添加部署名称
			.addZipInputStream(inputStream)//添加压缩流
			.deploy();//完成部署
	}
	
	/**
	 * 分页查询流程部署信息
	 * @return 封装好结果集的分页信息实体
	 */
	public PageInfo<Deployment> findDeploymentByPage(
			Integer targetPage, Integer pageSize, String deployName) {
		
		//1.判断是否需要按部署名称查询,如有需要加上条件
		DeploymentQuery query = null;
		if (StringUtils.hasText(deployName)) { //非空
			query = repositoryService.createDeploymentQuery().deploymentNameLike("%" + deployName + "%").orderByDeploymenTime().desc();
		} else {
			query = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc();
		}
		
		//2.看看应该从第几条记录去取
		Integer start = (targetPage-1) * pageSize;
		
		//3.得到流程定义列表
		List<Deployment> deployments = query.listPage(start, pageSize);
		
		//4.设置分页信息的参数
		
		if (StringUtils.hasText(deployName)) { //非空
			query = repositoryService.createDeploymentQuery().deploymentNameLike("%" + deployName + "%").orderByDeploymenTime().desc();
		} else {
			query = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc();
		}
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		Page<Deployment> page = new Page<>(targetPage, pageSize);
		page.setTotal(query.count());
		page.addAll(deployments);
		PageInfo<Deployment> pageInfo = new PageInfo<Deployment>(page, 5);
		
		//5.返回分页实体
		return pageInfo;
	}
	
	/**
	 * 分页查询流程定义
	 * @return 封装好结果集的分页信息实体
	 */
	public PageInfo<ProcessDefinition> findPorcessDefinationByPage(
			Integer targetPage, Integer pageSize, String procName) {
		
		//1.根据是否要按流程名称查询生成不同的查询对象
		ProcessDefinitionQuery query = null;
		if (StringUtils.hasText(procName)) {
			query = repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%" + procName + "%").orderByProcessDefinitionVersion().desc();
		} else {
			query = repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc();
		}
		
		//2.看看应该从第几条记录去取，取到哪里为止
		Integer start = (targetPage - 1) * pageSize;
		
		//3.得到流程定义列表
		List<ProcessDefinition> definitions = query.listPage(start, pageSize);
		
		//4.设置分页信息的参数
		Page<ProcessDefinition> page = new Page<>(targetPage, pageSize);
		page.setTotal(query.count());
		page.addAll(definitions);
		PageInfo<ProcessDefinition> pageInfo = new PageInfo<ProcessDefinition>(page, 5);
		
		//5.返回分页实体
		return pageInfo;
	}
	
	/**
	 * 使用部署对象ID，删除流程定义
	 */
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}
	
	/**
	 * 使用部署对象ID和资源图片名称，获取图片的输入流
	 */
	public InputStream findImageInputStream(String deploymentId,
			String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
}

package cn.edu.nuc.article.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
import org.springframework.stereotype.Service;

import cn.edu.nuc.article.util.KeyProvider;

/**
 * 文件服务（调用 JackRabbit 管理上传后的文件）
 * 
 * JackRabbit实现了JSR 170规范，其管理文件的方式是通过维护一棵文件树来管理。
 * 
 * 
 * @author 王凯
 *
 */
@Service("fileService")
public class FileService {

	/**
	 * RMI地址
	 */
	private final static String RMI = "http://localhost:7000/rmi";

	/**
	 * 仓库
	 */
	private static Repository repository = null;

	/**
	 * 初始化仓库
	 * 
	 * @return
	 */
	public Repository init() {
		if (null == repository) {
			try {
				repository = new URLRemoteRepository(RMI);
			} catch (MalformedURLException e) {
				System.out.println("----------------------JackRabbit 仓库初始化失败----------------------");
				e.printStackTrace();
			}
		}
		return repository;
	}

	/**
	 * 获取Session
	 * 
	 * @return
	 */
	public Session getSession() {
		Session session = null;
		try {
			session = init().login(new SimpleCredentials("admin", "admin".toCharArray()));
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return session;
	}

	/**
	 * 向仓库中存入一个文件
	 * @param is 待存入文件的输入流
	 * @return
	 */
	public String save(InputStream is) {
		
		//获取一个UUID作为文件id
		String fileId = KeyProvider.getPrimaryKey();
		
		//获取Session
		Session session = getSession();
		try {
			//获取根节点
			Node root = session.getRootNode();
			
			//给根节点添加一个文件节点
			Node filenode = root.addNode(fileId, "nt:file");
			
			//给文件节点添加一个资源节点
			Node resourcenode = filenode.addNode("jcr:content", "nt:resource");
			
			//设置资源节点的MIME类型
			resourcenode.setProperty("jcr:mimeType", "application/octest-stream");
			
			//设置待存入文件的输入流
			resourcenode.setProperty("jcr:data", is);
			
			//设置编码
			resourcenode.setProperty("jcr:encoding", "UTF-8");
			
			//保存文件
			session.save();
			
			//关闭输入流
			is.close();
			
			//注销Session
			session.logout();
		} catch (RepositoryException e) {
			System.out.println("---------------------上传文件过程中出现异常------------------------");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("---------------------上传文件过程中出现异常------------------------");
			e.printStackTrace();
		}
		return fileId;
	}

	/**
	 * 按Fileid取出单个文件（相当于下载）
	 * @param fileId 保存文件时拿到的FileId
	 * @return
	 */
	public InputStream getByFileId(String fileId) {
		
		//用于读取文件的输入流对象
		InputStream is = null;
		
		//获取Session
		Session session = getSession();
		
		try {
			//获取根节点
			Node root = session.getRootNode();
			
			//按照FileId取出符合条件的节点
			NodeIterator filenodeite = root.getNodes(fileId);
			
			if (filenodeite.hasNext()) {
				//如果能找到相关记录
				while (filenodeite.hasNext()) {
					
					//取出一个文件节点
					Node filenode = filenodeite.nextNode();
					
					//取出这个文件结点下的资源子节点
					NodeIterator resourcenodeite = filenode.getNodes();
					
					//如果有资源子节点
					while (resourcenodeite.hasNext()) {
						
						//取出一个资源节点
						Node resourcenode = resourcenodeite.nextNode();
						
						//找出数据域
						if (resourcenode.getName().equals("jcr:content")) {
							
							//得到文件输入流
							is = resourcenode.getProperty("jcr:data").getStream();
						}
					}
				}
			}
			
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	/**
	 * 按Fileid删除文件
	 * @param fileId 保存文件时拿到的FileId
	 * @return
	 */
	public void delete(String fileId) {
		
		//获取Session
		Session session = getSession();
		
		try {
			//获取根节点
			Node root = session.getRootNode();
			
			//按照FileId取出符合条件的节点
			NodeIterator filenodeite = root.getNodes(fileId);
			
			filenodeite.remove();
			
			session.save();
			
			//注销Session
			session.logout();
		} catch (RepositoryException e) {
			System.out.println("----------------------删除过程中出错-------------------------");
			e.printStackTrace();
		}
	}
}

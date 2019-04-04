package cn.edu.nuc.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.article.dao.ReceiveMapper;

/**
 * 公文接收信息Service
 * @author 王凯
 *
 */
@Service
public class ReceiveService {

	/**
	 * 公文接收信息Mapper
	 */
	@Autowired
	private ReceiveMapper receiveMapper;
	
	/**
	 * 添加公文接收信息
	 * @param receive
	 * @return
	 */
	public boolean addReceive(Integer articleId, Integer receiverId) {
		
		return receiveMapper.insertSelective(articleId, receiverId) > 0;
	}
	
	/**
	 * 删除公文接收信息
	 * @param articleId
	 * @return
	 */
	public boolean deleteReceive(Integer articleId) {
		
		return receiveMapper.deleteByArticleId(articleId) > 0;
	}
	
}

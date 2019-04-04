package cn.edu.nuc.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nuc.article.dao.AttachmentMapper;
import cn.edu.nuc.article.entity.Attachment;

/**
 * 附件业务逻辑
 * @author 王凯
 *
 */
@Service
public class AttachmentService {
	
	/**
	 * 附件Mapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;
	
	/**
	 * 删除附件
	 * @param attachmenId
	 * @return
	 */
	public boolean deleteAttachment(Integer attachmenId) {
		
		return attachmentMapper.deleteByPrimaryKey(attachmenId) > 0;
	}
	
	/**
	 * 添加一个附件
	 * @param attachment
	 * @return
	 */
	public boolean addAttachment(Attachment attachment) {
		
		return attachmentMapper.insertSelective(attachment) > 0;
	}
	
	/**
	 * 模糊查询
	 * @param attachment
	 * @return
	 */
	public List<Attachment> findByKeyword(Attachment attachment) {
		
		return attachmentMapper.selectByKeyword(attachment);
	}

}

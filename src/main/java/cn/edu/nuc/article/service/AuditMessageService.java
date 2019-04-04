package cn.edu.nuc.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nuc.article.dao.AuditMessageMapper;
import cn.edu.nuc.article.entity.AuditMessage;

/**
 * 公文审核结果Service
 * @author 王凯
 *
 */
@Service
public class AuditMessageService {

	/**
	 * 公文审核信息Mapper
	 */
	@Autowired
	private AuditMessageMapper auditMessageMapper;
	
	/**
	 * 添加公文审核信息
	 * @param auditMessage 公文审核信息
	 * @return
	 */
	@Transactional
	public boolean addAuditMessage(AuditMessage auditMessage) {
		
		return auditMessageMapper.insertSelective(auditMessage) > 0;
	}
	
}

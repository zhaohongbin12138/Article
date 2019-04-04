package cn.edu.nuc.article.dao;

import cn.edu.nuc.article.entity.AuditMessage;

/**
 * 公文审核Mapper
 * @author 王凯
 *
 */
public interface AuditMessageMapper {

	/**
	 * 添加一条公文审核记录
	 * @param record
	 * @return
	 */
    int insertSelective(AuditMessage record);

}
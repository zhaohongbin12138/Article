package cn.edu.nuc.article.dao;

import java.util.List;

import cn.edu.nuc.article.entity.Attachment;

/**
 * 附件Mapper
 * @author 王凯
 *
 */
public interface AttachmentMapper {
	
	/**
	 * 按主键删除
	 * @param attachmentid
	 * @return
	 */
    int deleteByPrimaryKey(Integer attachmentid);

    /**
     * 按主键插入
     * @param record 待插入记录
     * @return
     */
    int insertSelective(Attachment record);

    /**
     * 级联模糊查询
     * @param attachment 模糊查询条件
     * @return
     */
    List<Attachment> selectByKeyword(Attachment attachment);
}
package cn.edu.nuc.article.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 公文接收信息Mapper
 * @author 王凯
 *
 */
public interface ReceiveMapper {
	
	/**
	 * 删除某一条公文下的所有接收信息
	 * @param articleid
	 * @return
	 */
    int deleteByArticleId(Integer articleid);

    /**
     * 公文添加一个接收人
     * @param record
     * @return
     */
    int insertSelective(@Param("articleId") Integer articleId, 
    			@Param("receiverId") Integer receiverId);
}
package cn.edu.nuc.article.dao;

import java.util.List;

import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.article.entity.Article;

/**
 * 公文Mapper
 * @author 王凯
 *
 */
public interface ArticleMapper {

	/**
	 * 插入一条公文信息（公文撰稿时调用）
	 * @param record
	 * @return
	 */
    int insertSelective(Article record);

    /**
     * 修改一条公文信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Article record);

    /**
     * 模糊查询全部
     * @param article
     * @return
     */
    List<Article> selectListAll(Article article);
    
    /**
     * 查与我有关的信息
     * @param article
     * @return
     */
    List<Article> selectMyReceiveList(Article article);
    
    /**
     * 查询审核结果中正在审核的公文
     * @param article
     * @return
     */
    List<Article> selectMyAuditList(Article article);
    
    /**
     * 查与我有关的信息
     * @param article
     * @return
     */
    List<Article> selectMyList(Article article);
    
    /**
     * 按照流程实例id加载公文信息
     * @param ids
     * @return
     */
    List<Article> selectByProcessInstances(
    		@Param("tasks") List<Task> tasks, 
    		@Param("title") String title, 
    		@Param("articlestates") List<Integer> articlestates);
    
    /**
     * 按id查询公文详细信息
     * @param articleid
     * @return
     */
    Article selectOne(@Param("articleid") Integer articleid,  
    		@Param("userId") Integer userId);
    
    /**
     * 检查公文是否可以被下载和查看
     * @param userId
     * @param articleid
     * @return
     */
    Long validateAccess(@Param("userId") Integer userId, 
    		@Param("articleid") Integer articleid);
    
    /**
     * 查询等待审核通过公文的数量
     * @param userId 用户id
     * @return
     */
    Long selectMyWaitingCount(Integer userId);
    
    /**
     * 查询被驳回的公文数量
     * @param userId 用户id
     * @return
     */
    Long selectMyFailCount(Integer userId);
    
    /**
     * 查询需要我审核的公文数量
     * @param userId 用户id
     * @return
     */
    Long selectMyDealCount(Integer userId);
    
    /**
     * 查询待收取公文数量
     * @param userId 用户id
     * @return
     */
    Long selectMyCountReceiver(Integer userId);
    
    /**
     * 撰稿人删除公文
     * @param articleid
     * @return
     */
    Integer deleteById(Integer articleid);
    
    /**
     * 检查公文标题是否重复
     * @param title
     * @return
     */
    Long validateTitle(@Param("title") String title, 
    		@Param("articleid") Integer articleid);
}
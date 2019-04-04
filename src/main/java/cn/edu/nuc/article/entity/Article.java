package cn.edu.nuc.article.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;

/**
 * 公文
 * @author 王凯
 *
 */
public class Article implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 公文id
	 */
    private Integer articleid;

    /**
     * 公文标题
     */
    private String title;

    /**
     * 发布时间
     */
    private Date publishtime;

    /**
     * 撰稿人id
     */
    private Integer copywriterId;
    
    /**
     * 存放级联查询出来的撰稿人信息（数据库没字段对应）
     */
    private User copywriter;

    /**
     * 审稿人id
     */
    private Integer auditorId;
    
    /**
     * 存放级联查询出来的审稿人信息（数据库没字段对应）
     */
    private User auditor;

    /**
     * 发布机构id
     */
    private Integer instId;
    
    /**
     * 存放级联查询出来的发布机构信息（数据库没字段对应）
     */
    private Institution institution;
    
    /**
     * 公文状态：
     * 0 审核中
     * 1 审核通过
     * 2 审核驳回
     * 3 公文发布
     * 4 公文删除
     */
    private Integer articlestate;
    
    /**
     * 流程实例Id
     */
    private String processinstanceId;

    /**
     * 点击量
     */
	private Long clickcount;
	
	/**
	 * 下载量
	 */
	private Long downloadcount;
	
	/**
	 * 附件
	 */
	private List<Attachment> attachments;
	
	/**
	 * 校验信息
	 */
	private List<AuditMessage> auditMessages;
	
	/**
	 * 接收人
	 */
	private List<User> receivers;
	
	/**
	 * 加载和自己有关的列表时使用
	 */
	private Integer userId;
	
	/**
	 * 任务
	 */
	private Task task;

	public Integer getArticleid() {
		return articleid;
	}

	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Integer getCopywriterId() {
		return copywriterId;
	}

	public void setCopywriterId(Integer copywriterId) {
		this.copywriterId = copywriterId;
	}

	public User getCopywriter() {
		return copywriter;
	}

	public void setCopywriter(User copywriter) {
		this.copywriter = copywriter;
	}

	public Integer getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Integer auditorId) {
		this.auditorId = auditorId;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Integer getArticlestate() {
		return articlestate;
	}

	public void setArticlestate(Integer articlestate) {
		this.articlestate = articlestate;
	}

	public String getProcessinstanceId() {
		return processinstanceId;
	}

	public void setProcessinstanceId(String processinstanceId) {
		this.processinstanceId = processinstanceId;
	}

	public Long getClickcount() {
		return clickcount;
	}

	public void setClickcount(Long clickcount) {
		this.clickcount = clickcount;
	}

	public Long getDownloadcount() {
		return downloadcount;
	}

	public void setDownloadcount(Long downloadcount) {
		this.downloadcount = downloadcount;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<AuditMessage> getAuditMessages() {
		return auditMessages;
	}

	public void setAuditMessages(List<AuditMessage> auditMessages) {
		this.auditMessages = auditMessages;
	}

	public List<User> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<User> receivers) {
		this.receivers = receivers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "\n----------------\n"
				+ "Article [articleid=" + articleid 
				+ ", \ntitle=" + title 
				+ ", \npublishtime=" + publishtime
				+ ", \ncopywriterId=" + copywriterId 
				+ ", \ncopywriter=" + copywriter 
				+ ", \nauditorId=" + auditorId
				+ ", \nauditor=" + auditor 
				+ ", \ninstId=" + instId 
				+ ", \ninstitution=" + institution 
				+ ", \narticlestate=" + articlestate 
				+ ", \nprocessinstanceId=" + processinstanceId 
				+ ", \nclickcount=" + clickcount
				+ ", \ndownloadcount=" + downloadcount 
				+ ", \nattachments=" + attachments 
				+ ", \nauditMessages=" + auditMessages 
				+ ", \nreceivers=" + receivers 
				+ ", \nuserId=" + userId + "]\n--------------\n";
	}

	/**
	 * 以公文id为唯一标示
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleid == null) ? 0 : articleid.hashCode());
		return result;
	}

	/**
	 * 以公文id为唯一标示
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (articleid == null) {
			if (other.articleid != null)
				return false;
		} else if (!articleid.equals(other.articleid))
			return false;
		return true;
	}   
}
package cn.edu.nuc.article.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 审核信息
 * @author 王凯
 *
 */
public class AuditMessage implements Serializable {
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 审核信息id（主键）
	 */
	private Integer auditid;

	/**
	 * 被审核公文id（外键）
	 * 由于经常通过公文查询审核信息，而从不需要根据审核信息查询公文，所以不需要建立审核信息到公文的映射
	 */
    private Integer articleId;

    /**
     * 审核日期
     */
    private Date auditdate;

    /**
     * 审核结果，0：驳回1：通过
     */
    private Integer auditresult;

    /**
     * 审核信息
     */
    private String auditmessage;

    public Integer getAuditid() {
        return auditid;
    }

    public void setAuditid(Integer auditid) {
        this.auditid = auditid;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Date getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(Date auditdate) {
        this.auditdate = auditdate;
    }

    public Integer getAuditresult() {
        return auditresult;
    }

    public void setAuditresult(Integer auditresult) {
        this.auditresult = auditresult;
    }

    public String getAuditmessage() {
        return auditmessage;
    }

    public void setAuditmessage(String auditmessage) {
        this.auditmessage = auditmessage == null ? null : auditmessage.trim();
    }

	@Override
	public String toString() {
		return "AuditMessage [auditid=" + auditid + ", articleId=" + articleId + ", auditdate=" + auditdate
				+ ", auditresult=" + auditresult + ", auditmessage=" + auditmessage + "]";
	}

	/**
	 * 以审核信息id作为唯一标示
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditid == null) ? 0 : auditid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditMessage other = (AuditMessage) obj;
		if (auditid == null) {
			if (other.auditid != null)
				return false;
		} else if (!auditid.equals(other.auditid))
			return false;
		return true;
	}
    
    
}
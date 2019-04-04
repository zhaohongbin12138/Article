package cn.edu.nuc.article.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 * @author 王凯
 *
 */
public class Log implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志id
	 */
    private Integer logid;

    /**
     * 操作名称
     */
    private String optname;

    /**
     * 业务id（非物理外键，但是却起到了物理外键的作用）
     */
    private Integer bussinessId;

    /**
     * 操作人id（物理外键）
     */
    private Integer operatorId;
    
    /**
     * 操作人（级联出来的对象，不是真实的数据库列）
     */
    private User operator;

    /**
     * 操作时间(就是取添加日志时候服务器当前系统的时间)
     */
    private Date opttime;

    /**
     * 操作人登陆的ip地址
     */
    private String ipaddress;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getOptname() {
        return optname;
    }

    public void setOptname(String optname) {
        this.optname = optname == null ? null : optname.trim();
    }

    public Integer getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Integer bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOpttime() {
        return opttime;
    }
    
    public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	@Override
	public String toString() {
		return "Log [logid=" + logid + ", optname=" + optname + ", bussinessId=" + bussinessId + ", operatorId="
				+ operatorId + ", operator=" + operator + ", opttime=" + opttime + ", ipaddress=" + ipaddress + "]";
	}

	//以logid作为唯一标示
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logid == null) ? 0 : logid.hashCode());
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
		Log other = (Log) obj;
		if (logid == null) {
			if (other.logid != null)
				return false;
		} else if (!logid.equals(other.logid))
			return false;
		return true;
	}
}
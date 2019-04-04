package cn.edu.nuc.article.entity;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 组织机构
 * @author 王凯
 *
 */
public class Institution implements Serializable {
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 机构id
	 */
    private Integer instid;

    /**
     * 机构名称
     */
    @NotNull(message="机构名称是必填项！")
    @NotBlank(message="机构名称不允许填写空白！")
    @Length(min=4, max=15, message="输入的机构名称长度必须在4~15个字符之间！")
    private String instname;

    /**
     * 机构地址
     */
    @NotBlank(message="机构地址不允许填写空白！")
    @Length(min=4, max=20, message="输入的机构地址长度必须在4~20个字符之间！")
    private String instaddr;

    /**
     * 邮编
     */
    @Digits(integer=6, fraction=0, message="邮编只可以由数字组成！")
    @Pattern(regexp="[0-9][0-9]{5}", message="邮编格式不合法！")
    private String postcode;

    /**
     * 机构状态：正常:1  禁用:0  被合并:-1
     */
    private Integer inststate;
    
    /**
     * 合并后的机构id（外键）
     */
    private Integer mergedinstid;
    
    /**
     * 被合并的机构信息
     */
    private Institution mergedInstitution;

	public Integer getInstid() {
		return instid;
	}

	public void setInstid(Integer instid) {
		this.instid = instid;
	}

	public String getInstname() {
		return instname;
	}

	public void setInstname(String instname) {
		this.instname = instname;
	}

	public String getInstaddr() {
		return instaddr;
	}

	public void setInstaddr(String instaddr) {
		this.instaddr = instaddr;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getInststate() {
		return inststate;
	}

	public void setInststate(Integer inststate) {
		this.inststate = inststate;
	}

	public Integer getMergedinstid() {
		return mergedinstid;
	}

	public void setMergedinstid(Integer mergedinstid) {
		this.mergedinstid = mergedinstid;
	}

	public Institution getMergedInstitution() {
		return mergedInstitution;
	}

	public void setMergedInstitution(Institution mergedInstitution) {
		this.mergedInstitution = mergedInstitution;
	}

	@Override
	public String toString() {
		return "Institution [instid=" + instid + ", instname=" + instname + ", instaddr=" + instaddr + ", postcode="
				+ postcode + ", inststate=" + inststate + ", mergedinstid=" + mergedinstid + ", mergedInstitution="
				+ mergedInstitution + "]";
	}

	/**
	 * 以机构id作为唯一标识
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((instid == null) ? 0 : instid.hashCode());
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
		Institution other = (Institution) obj;
		if (instid == null) {
			if (other.instid != null)
				return false;
		} else if (!instid.equals(other.instid))
			return false;
		return true;
	}

}
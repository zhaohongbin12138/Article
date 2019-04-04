package cn.edu.nuc.article.entity;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 角色
 * @author 王凯
 *
 */
public class Role implements Serializable {
	
    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	private Integer roleid;

	/**
	 * 角色名称
	 */
	@NotNull(message="角色名称不能为空！")
	@NotBlank(message="角色名称不能为空！")
	@Length(min = 4, max = 15, message="角色名称长度不合法！")
	@Pattern(regexp="[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+", message="角色名称只能输入中文汉字！")
    private String rolename;

    /**
     * 角色描述
     */
	@NotBlank(message="角色描述不能为空白！")
	@Length(max=300, message="角色描述过长！")
    private String roledesc;

    /**
     * 角色状态
     */
    private Integer rolestate;

    /**
     * 角色能够使用的功能列表
     */
    private List<Function> functionList;

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public Integer getRolestate() {
		return rolestate;
	}

	public void setRolestate(Integer rolestate) {
		this.rolestate = rolestate;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", rolename=" + rolename + ", roledesc=" + roledesc + ", rolestate="
				+ rolestate + ", functionList=" + functionList + "]";
	}

	/**
	 * 以角色id作为唯一标识
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((roleid == null) ? 0 : roleid.hashCode());
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
		Role other = (Role) obj;
		if (roleid == null) {
			if (other.roleid != null)
				return false;
		} else if (!roleid.equals(other.roleid))
			return false;
		return true;
	}

}
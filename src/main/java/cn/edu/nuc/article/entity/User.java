package cn.edu.nuc.article.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import cn.edu.nuc.article.validategroup.UserValidateGroupForInsert;
import cn.edu.nuc.article.validategroup.UserValidateGroupForPasswordModify;
import cn.edu.nuc.article.validategroup.UserValidateGroupForUpdate;
import cn.edu.nuc.article.validategroup.UserValidateGroupForUserModify;

/**
 * 用户实体类
 * @author 王凯
 *
 */
public class User implements Serializable{
	
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id（主键）
	 */
    private Integer userid;

    /**
     * 登录名
     */
    @NotNull(message="登录名不可以为空！", groups={UserValidateGroupForInsert.class})
    @NotBlank(message="登录名不可以填写空格！", groups={UserValidateGroupForInsert.class})
    @Length(min = 4, max = 30, message="登录名输入长度不合法！", 
    	groups={UserValidateGroupForInsert.class})
    @Pattern(message="登录名登录名只能由字母和数字组成！",regexp="[A-Za-z0-9]+",
    	groups={UserValidateGroupForInsert.class})
    private String loginname;

    /**
     * 密码
     */
    @NotNull(message="密码不可以为空！", 
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForPasswordModify.class})
    @NotBlank(message="密码不可以填写空格！", 
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForPasswordModify.class})
    @Length(max = 30, message="密码输入长度不合法！",
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForPasswordModify.class})
    private String password;
    
    /**
     * 旧密码（数据库没有对应列）
     */
    @NotNull(message="旧密码不可以为空！", 
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForPasswordModify.class})
    @NotBlank(message="旧密码不可以填写空格！", 
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForPasswordModify.class})
    @Length(max = 30, message="旧密码输入长度不合法！",
    		//校验组指定校验规则什么时候生效
    		groups={UserValidateGroupForPasswordModify.class})
    private String oldPassword;

    /**
     * 用户真实姓名
     */
    @NotNull(message="用户真实姓名不可以为空！", 
    		groups={UserValidateGroupForInsert.class})
    @NotBlank(message="用户真实姓名不可以填写空格！",
    		groups={UserValidateGroupForInsert.class})
    @Length(min = 2, max = 20, message="用户真实姓名输入长度不合法！",
    		groups={UserValidateGroupForInsert.class})
    @Pattern(regexp="[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+", 
    		message="用户真实姓名只能输入中文汉字！",
    		groups={UserValidateGroupForInsert.class})
    private String usertruename;

    /**
     * 职称
     */
    @NotNull(message="职称不可以为空！")
    @NotBlank(message="职称不可以填写空格！")
    @Pattern(regexp="[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+", 
    		message="职称只能输入中文汉字！",
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForUpdate.class,
    				UserValidateGroupForUserModify.class})
    private String job;

    /**
     * 手机号
     */
    @NotBlank(message="手机号不可以填写空格！", 
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForUpdate.class,
    				UserValidateGroupForUserModify.class})
    @Pattern(regexp="1[3|5|7|8]\\d{9}", message="手机号格式不正确！",
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForUpdate.class,
    				UserValidateGroupForUserModify.class})
    private String phone;

    /**
     * 邮箱
     */
    @Email(message="您输入的邮箱格式不合法！", 
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForUpdate.class,
    				UserValidateGroupForUserModify.class})
    @NotBlank(message="邮箱不可以填写空格！",
    		groups={UserValidateGroupForInsert.class,
    				UserValidateGroupForUpdate.class,
    				UserValidateGroupForUserModify.class})
    private String email;

    /**
     * 所属机构（外键）
     */
    private Integer instId;
    
    /**
     * 所属机构信息
     */
    private Institution institution;

    /**
     * 角色id（外键）
     */
    private Integer roleId;
    
    /**
     * 用户角色
     */
    private Role role;

    /**
     * 用户状态（1正常，0禁用）
     */
    private Integer userstate;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getUsertruename() {
		return usertruename;
	}

	public void setUsertruename(String usertruename) {
		this.usertruename = usertruename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getUserstate() {
		return userstate;
	}

	public void setUserstate(Integer userstate) {
		this.userstate = userstate;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", loginname=" + loginname + ", password=" + password + ", oldPassword="
				+ oldPassword + ", usertruename=" + usertruename + ", job=" + job + ", phone=" + phone + ", email="
				+ email + ", instId=" + instId + ", institution=" + institution + ", roleId=" + roleId + ", role="
				+ role + ", userstate=" + userstate + "]";
	}

	/**
	 * 用用户id作为唯一标识
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userid == null) ? 0 : userid.hashCode());
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
		User other = (User) obj;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

    
}
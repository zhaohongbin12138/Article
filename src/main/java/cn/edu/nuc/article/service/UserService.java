package cn.edu.nuc.article.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nuc.article.dao.RoleMapper;
import cn.edu.nuc.article.dao.UserMapper;
import cn.edu.nuc.article.entity.Institution;
import cn.edu.nuc.article.entity.Role;
import cn.edu.nuc.article.entity.User;
import cn.edu.nuc.article.util.ExcelTools;
import cn.edu.nuc.article.util.MD5Helper;

/**
 * 用户service
 * @author 王凯
 *
 */
@Service
public class UserService {

	/**
	 * 用户Mapper
	 */
	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 角色Mapper
	 */
	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 机构Service
	 */
	@Autowired
	private InstitutionService institutionService;
	
	/**
	 * 角色Service
	 */
	@Autowired
	private RoleService roleService;
	
	/**
	 * 创建SqlSession的工厂
	 */
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean updateUser(User user) {
		
		return userMapper.updateByPrimaryKeySelective(user) > 0;
	}
	
	/**
	 * 逐个添加用户
	 * @param user
	 * @return
	 */
	@Transactional
	public boolean addUser(User user) {
		
		return userMapper.insertSelective(user) > 0;
	}
	
	/**
	 * 批量添加用户（读取Excel并添加到数据库）
	 * @param inputStream 读取上传的Excel数据表格的InputStream
	 * @return 操作结果：true表示操作成功， false表示操作失败 
	 */
	@Transactional
	public boolean addUserBatch(InputStream inputStream){
		
		//1.从Excel表格读取数据,数据存放在map中，键是从1开始的数字，代表行号，值是读取的数据，是字符串格式，以"-"作为分隔符，需要解析
		ExcelTools excelReader = new ExcelTools();
		Map<Integer, String> map = excelReader.readExcelContent(inputStream);
		
		//2.将读取的数据封装为User对象并形成列表
		
		//2.1 获取Map中的每一个Entry
		
		List<User> users = new ArrayList<>();
		
		Set<Entry<Integer, String>> entry = map.entrySet();
		
		for (Entry<Integer, String> entryiter : entry) {
			
			//2.2 读取出Entry中的值，即用户信息字符串
			String userdatastring = entryiter.getValue();
			
			//2.3 通过分隔符"-"来解析数据，得到一个字符串数组，这个字符串数组代表一行数据
			String[] dataarray = userdatastring.split("-");
			
			//2.4 使用取出的字符串数组拼装出一个User对象
			User user = new User();
			
			String loginname = dataarray[0];
			if (userMapper.loginnameValidate(null, loginname).size() > 0) {
				return false;
			}
			
			user.setLoginname(loginname);
			
			//对用户输入的密码进行加密
			String password = dataarray[1].split("\\.")[0];
			MD5Helper md5Helper = new MD5Helper();
			String afterEncodepassword = md5Helper.getTwiceMD5ofString(password);
			user.setPassword(afterEncodepassword);
			
			user.setUsertruename(dataarray[2]);
			user.setJob(dataarray[3]);
			
			String phone = dataarray[4];
			Double doublevalue = Double.parseDouble(phone);
			user.setPhone(Long.toString(doublevalue.longValue()));
			user.setEmail(dataarray[5]);
			
			Institution institution = new Institution();
			institution.setInstname(dataarray[6]);
			institution = institutionService.findByKeyword(institution).get(0);
			user.setInstId(institution.getInstid());
			
			Role role = new Role();
			role.setRolename(dataarray[7]);
			role = roleService.getByKeyword(role).get(0);
			user.setRoleId(role.getRoleid());
			
			user.setUserstate(1);
			
			//2.5 将拼装好的User对象加入到集合中
			users.add(user);
		}

		//3.使用批量处理方式将数据加入数据库
		
		//3.1 通过SessionFactory得到批量处理的SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		//3.2 获得可以批量处理的UserMapper
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		
		//3.3 对每一个用户执行添加操作，通过“攒Sql”方式，全部编译完成后发给数据库，数据库一次性执行完成
		try {
			for (User user : users) {
				if (userMapper.insertSelective(user) == 0) {
					//添加用户过程中出错
					sqlSession.rollback();
					sqlSession.close();
					return false;
				}
			}
			
			//3.4 批量处理方式需要手动提交事务
			sqlSession.commit();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			//3.5 出现异常要回滚事务
			sqlSession.rollback();
			return false;
		} finally {
			
			//3.6 无论是否出现异常，操作结束后都要关闭session
			sqlSession.close();
		}
	}
	
	/**
	 * 找出所有未被禁用的审核人员
	 * @return
	 */
	public List<User> findValidAuditor(Integer instid) {
		
		//设置查询的筛选信息
		User user = new User();
		user.setUserstate(1);
		
		//找出审核人员的id
		Role role = new Role();
		role.setRolename("审核");
		role = roleMapper.selectByKeyword(role).get(0);
		
		user.setRoleId(role.getRoleid());

		//如果要限制企业
		if (instid != null) {
			user.setInstId(instid);
		}
		
		return userMapper.selectByKeyWord(user);
	}
	
	/**
	 * 找出未被禁用的用户（作为备选项）
	 * @return
	 */
	public List<User> findValidUser() {
		
		User user = new User();
		user.setUserstate(1);
		
		return userMapper.selectByKeyWord(user);
	}
	
	/**
	 * 检查登录名是否重复
	 * @param userid 用户id
	 * @param loginname 登录名
	 * @return true 表示重复， false 表示不重复
	 */
	public boolean hasSameName(Integer userid, String loginname) {
		
		return userMapper.loginnameValidate(userid, loginname).size() > 0;
	}
	
	/**
	 * 登录检查
	 * @param loginname 登录名
	 * @param password 密码
	 * @return true 表示登录检查通过, false 表示不通过
	 */
	public User loginValidate(String loginname, String password) {
		
		return userMapper.loginValidate(loginname, password);
	}
	
	/**
	 * 模糊查询
	 * @return
	 */
	public List<User> findByKeyword(User user) {
		
		return userMapper.selectByKeyWord(user);
	}
	
	/**
	 * 按用户id查询
	 * @param userid
	 * @return
	 */
	public User findbyId(Integer userid) {
		
		return userMapper.selectByPrimaryKey(userid);
	}
}

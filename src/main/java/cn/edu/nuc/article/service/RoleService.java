package cn.edu.nuc.article.service;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nuc.article.dao.RoleMapper;
import cn.edu.nuc.article.entity.Role;

/**
 * 角色Service
 * @author 王凯
 *
 */
@Service
public class RoleService {
	
	/**
	 * 角色Mapper
	 */
	@Autowired
	private RoleMapper roleMapper;
	
	/**
	 * 创建SqlSession的工厂类
	 */
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 判断是否有重名角色
	 * @param roleid 角色id
	 * @param rolename 角色名称
	 * @return
	 */
	public boolean hasSameRole(Integer roleid, String rolename) {
		
		return roleMapper.hasSameRole(roleid, rolename).size() > 0;
	}
	
	/**
	 * 更新权限
	 * @param roleid
	 * @param funids
	 * @return
	 */
	@Transactional
	public boolean updateRoleright(Integer roleid, Integer[] funids) {
		
		//通过SessionFactory得到批量处理的SqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		
		RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
		
		try {
			//删掉这个角色的旧权限记录
			roleMapper.deleteOldRoleRights(roleid);
			
			//添加新的权限记录
			for (Integer funid : funids) {
				if(roleMapper.insertNewRoleRightInfo(roleid, funid) == 0) { //添加新权限失败
					
					//回滚操作
					sqlSession.rollback();
					sqlSession.close();
					return false;
				}
			}

			//提交事务，批量处理
			sqlSession.commit();
			return true;
		} catch (Exception e) {
			//出现异常的时候回滚事务
			e.printStackTrace();
			sqlSession.rollback();
			return false;
		} finally {
			//关闭SqlSession
			sqlSession.close();
		}
	}
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	@Transactional
	public boolean updateRole(Role role) {
		
		return roleMapper.updateByPrimaryKeySelective(role) > 0;
	}
	
	/**
	 * 添加角色信息
	 * @param role
	 * @return
	 */
	@Transactional
	public boolean addRole(Role role) {
		
		return roleMapper.insertSelective(role) > 0;
	}
	
	/**
	 * 按主键查询
	 * @param roleid
	 * @return
	 */
	public Role findByIdCascade(Integer roleid) {
		
		return roleMapper.selectByPrimaryKeyCascade(roleid);
	}
	
	/**
	 * 按主键查询
	 * @param roleid
	 * @return
	 */
	public Role findById(Integer roleid) {
		
		return roleMapper.selectByPrimaryKey(roleid);
	}
	
	/**
	 * 模糊查询
	 * @param role
	 * @return
	 */
	public List<Role> getByKeyword(Role role) {
		
		return roleMapper.selectByKeyword(role);
	}
	
	/**
	 * 级联模糊查询
	 * @param role
	 * @return
	 */
	public List<Role> getByKeywordCascade(Role role) {
		
		return roleMapper.selectByKeywordCascade(role);
	}
}

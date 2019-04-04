package cn.edu.nuc.article.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nuc.article.dao.InstitutionMapper;
import cn.edu.nuc.article.entity.Institution;

/**
 * 机构Service
 * @author 王凯
 *
 */
@Service
public class InstitutionService {

	/**
	 * 机构Mapper
	 */
	@Autowired
	private InstitutionMapper institutionMapper;
	
	/**
	 * 机构合并
	 * @param instid1 被合并机构
	 * @param instid2 合并到哪一个机构
	 * @return
	 */
	@Transactional
	public boolean merge(Integer[] instid1, Integer instid2) {
		
		//1.先检查被合并的机构中是否有机构下面还有用户，一旦发现有，立刻终止操作
		for (Integer instid : instid1) {
			if (institutionMapper.isInstitutionHasUser(instid) != 0) {
				return false;
			}
		}
		
		//2.将被合并机构的状态设置为已合并状态，然后将被合并机构合并到的新机构id为被合并机构填上，表示这个机构被合并
		for (Integer instid : instid1) {
			
			//2.1找出机构信息
			Institution institution = institutionMapper.selectByPrimaryKey(instid);
			
			//2.2修改其状态为被合并
			institution.setInststate(-1);
			
			//2.3 设置合并后的新机构id
			institution.setMergedinstid(instid2);
			
			//2.4 向数据库同步结果
			institutionMapper.updateByPrimaryKeySelective(institution);
		}
		
		//3.操作完成
		return true;
	}
	
	/**
	 * 找出下面没有用户的机构
	 * @return
	 */
	public List<Institution> selectInstitutionNoUserUnder(){
		
		return institutionMapper.selectInstitutionNoUserUnder();
	}
	
	/**
	 * 查询出下面有用户的机构（未被合并）
	 * @return
	 */
	public List<Institution> selectInstitutionHasUserUnderAndValid(){
		
		return institutionMapper.selectInstitutionHasUserUnderAndValid();
	}
	
	/**
	 * 判断机构下面是否还有用户
	 * @param instid
	 * @return
	 */
	public boolean isInstitutionHasUser(int instid) {
		
		return institutionMapper.isInstitutionHasUser(instid) == 0;
	}
	
	/**
	 * 修改机构信息
	 * @param institution
	 * @return
	 */
	@Transactional
	public boolean updateInstitution(Institution institution) {
		
		return institutionMapper.updateByPrimaryKeySelective(institution) > 0;
	}
	
	/**
	 * 添加机构信息
	 * @param institution 机构信息
	 * @return
	 */
	@Transactional
	public boolean addInstitution(Institution institution) {
		
		return institutionMapper.insertSelective(institution) > 0;
	}
	
	/**
	 * 添加和修改操作检查重名
	 * @param instid
	 * @param instname
	 * @return true表示重名， false表示不重名
	 */
	public boolean hasSameInstitution(Integer instid, String instname) {
		
		return institutionMapper.hasSameInstitution(instid, instname).size() > 0;
	}
	
	/**
	 * 模糊查询
	 * @param institution 携带查询条件
	 * @return
	 */
	public List<Institution> findByKeyword(Institution institution) {
		
		return institutionMapper.selectByKeyWord(institution);
	}
	
	/**
	 * 按主键查询
	 * @param instid
	 * @return
	 */
	public Institution findById(Integer instid) {
		
		return institutionMapper.selectByPrimaryKey(instid);
	}
}

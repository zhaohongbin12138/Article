package cn.edu.nuc.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.edu.nuc.article.entity.Institution;

/**
 * 机构Mapper
 * @author 王凯
 *
 */
public interface InstitutionMapper {
	
	/**
	 * 动态插入
	 * @param record
	 * @return
	 */
    int insertSelective(Institution record);

    /**
     * 按主键查询
     * @param instid 机构id
     * @return
     */
    Institution selectByPrimaryKey(Integer instid);
    
    /**
     * 模糊查询
     * @param institution
     * @return
     */
    List<Institution> selectByKeyWord(Institution institution);
    
    /**
     * 查重
     * @param instid
     * @param instname
     * @return
     */
    List<Institution> hasSameInstitution(@Param("instid") Integer instid, @Param("instname") String instname);
    
    /**
     * 查询出下面没有用户且未被合并的机构
     * @return
     */
    List<Institution> selectInstitutionNoUserUnder();
    
    /**
     * 查询出下面有用户的机构（未被合并）
     * @return
     */
    List<Institution> selectInstitutionHasUserUnderAndValid();

    /**
     * 动态更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Institution record);
    
    /**
     * 判断机构下面是否还有用户（机构合并的时候用）
     * @param instid 机构id
     * @return
     */
    long isInstitutionHasUser(Integer instid);

}
package cn.edu.nuc.article.dao;

import java.util.List;

import cn.edu.nuc.article.entity.Log;

/**
 * 日志Mapper
 * @author 王凯
 *
 */
public interface LogMapper {

	/**
	 * 选择性插入
	 * @param record
	 * @return
	 */
    int insertSelective(Log record);

    /**
     * 模糊查询
     * @param log
     * @return
     */
    List<Log> selectByKeyword(Log log);
}
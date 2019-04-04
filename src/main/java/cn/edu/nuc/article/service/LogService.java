package cn.edu.nuc.article.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nuc.article.dao.LogMapper;
import cn.edu.nuc.article.entity.Log;

/**
 * 日志逻辑
 * @author 王凯
 *
 */
@Service
public class LogService {

	/**
	 * 日志Mapper
	 */
	@Autowired
	private LogMapper logMapper;
	
	/**
	 * 模糊查询
	 * @param log
	 * @return
	 */
	public List<Log> findByKeyword(Log log) {
		
		return logMapper.selectByKeyword(log);
	}
	
	/**
	 * 添加一条日志记录
	 * @param log
	 * @return
	 */
	@Transactional
	public boolean addLog(Log log) {
		log.setOpttime(new Date());
		return logMapper.insertSelective(log) > 0;
	}
}

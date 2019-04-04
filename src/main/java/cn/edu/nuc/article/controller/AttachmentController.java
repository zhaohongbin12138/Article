package cn.edu.nuc.article.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.nuc.article.entity.Attachment;
import cn.edu.nuc.article.service.AttachmentService;

/**
 * 附件Controller
 * @author 王凯
 *
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController {

	/**
	 * 附件业务逻辑
	 */
	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * 删除附件
	 * @param map
	 * @param attachmenId
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(Map<String, Object> map, Integer attachmenId) {
		
		boolean result = attachmentService.deleteAttachment(attachmenId);
		
		map.put("result", result);
		
		if (result == true) {
			map.put("msg", "删除附件成功!");
		} else {
			map.put("msg", "删除附件失败!");
		}
		
		return "forward:/attachment/attachments";
	}
	
	/**
	 * 分页+模糊查询
	 * @param pageNo 当前页
	 * @param pageCount 每页记录数
	 * @param function 待查参数
	 * @return
	 */
	@RequestMapping("/attachments")
	public String findByKeyword(Map<String, Object> map,
			@RequestParam(value="pageNo", defaultValue="1", required=false) Integer pageNo,
			@RequestParam(value="pageCount", defaultValue="10", required=false) Integer pageCount,
			@RequestParam(value="keyword", required=false) String keyword) {
		
		// 引入PageHelper分页插件
		// 在查询之前只需要调用，传入页码，以及每页的大小
		PageHelper.startPage(pageNo, pageCount);
		
		// 分页查询得到结果集
		List<Attachment> attachments;
		
		if (StringUtils.hasText(keyword)) {
			Attachment attachment = new Attachment();
			attachment.setFilename(keyword);
			attachments = attachmentService.findByKeyword(attachment);
		} else {
			attachments = attachmentService.findByKeyword(null);
		}
		
		
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
		// 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
		PageInfo<Attachment> page = new PageInfo<Attachment>(attachments, 5);
		
		//保存结果集带到页面显示
		map.put("page", page);
		map.put("pageNo", pageNo);
		map.put("pageCount", pageCount);
		
		//保存模糊查询条件以便回显
		map.put("keyword", keyword);
		
		return "attachment/attachmentManage";

	}
	
}

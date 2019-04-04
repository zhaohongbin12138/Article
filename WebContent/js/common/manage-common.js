//管理界面通用

//分页操作
function gotoPage(page) {
	
	//根据实际情况生成发送请求的URL
	var url = getRequestURL(page);
	
	//发送GET请求，实现页面跳转
	window.location.href = url;
}

//根据确认框决定删除操作是否执行
function confirmDelete(node) {
	
	//要删除/禁用/移除的对象名称
	var functionName = $(node).parent().parent().find("td:first").text();
	
	//要执行操作的名称，根据按钮名称加载
	var option = $(node).text();
	
	//组成提示语
	var alertContent = "确定要" + option + "“" + functionName + "”吗？";
	
	//弹出确认框，根据用户的选择执行操作
	if(window.confirm(alertContent)) { //确认执行操作
		return true;
	}
	
	//不执行操作
	return false;
}


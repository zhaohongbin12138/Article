<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  //获取静态资源的绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
							+ request.getServerPort() + path+"/";
%>
<!DOCTYPE html>
<html lang="en" class="app">

	<head>
		<meta charset="utf-8" />
		<title>公文撰稿</title>
		<meta name="description" content="app, web app, responsive, admin dashboard, admin, flat, flat ui, ui kit, off screen nav" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
		<!-- -->
		
	    <link rel="stylesheet" href="<%= basePath %>js/fuelux/fuelux.css" type="text/css"/>
	    <link rel="stylesheet" href="<%= basePath %>js/slider/slider.css" type="text/css"/>
	    
	    <!-- Required Stylesheets -->
		<link href="<%= basePath %>js/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
			
		<link rel="stylesheet" href="<%= basePath %>css/app.v2.css" type="text/css" />

		<!-- 注意：app.v2.js必须放在jquery-3.2.0.min.js和bootstrap-treeview.min.js文件之上，否则无法加载树 -->
		<script type="text/javascript" src="<%= basePath %>js/app.v2.js" ></script>
		
		<script src="<%= basePath %>js/jquery/jquery-3.2.0.min.js"></script>
		<script src="<%= basePath %>js/bootstrap-jquery-tree/dist/bootstrap-treeview.min.js"></script>     
	</head>

	<body>
		<section class="vbox">
			<section>
				<section class="hbox stretch">
					<section id="content">
						<section class="vbox">
							
							<section class="scrollable padder">
								<!-- 面包屑导航 -->
								<ul class="breadcrumb no-border no-radius b-b b-light pull-in">
									<li>
										<a href="<%= basePath %>toWelcome">
											<i class="fa fa-home"></i> 首页
										</a>
									</li>
									<li class="active">公文撰稿</li>
								</ul>
								
								<!-- 表格标题 -->
								<div class="m-b-md">
									<h2 class="m-b-none">公文撰稿</h2>
									
								</div>
								
								<!-- 增删改结果提示 -->
								<jsp:include page="/common/top.jsp"></jsp:include>
								
								<form id="articleuploadForm" action="<%= basePath %>article/addArticle" method="post" enctype="multipart/form-data" 
									class="form-horizontal" data-validate="parsley">
									
									<div>
	                            		<a href="<%= basePath %>article/getMyHistoryList" class="btn btn-default"><i class="fa  fa-chevron-left"></i>返回</a>
	                            		<button type="submit" class="btn btn-dark btn-s-xs" onclick="return validate();">提交公文</button>
									</div>
									<br />
									
									<div class="alert alert-success">
                  						<h4><b>公文撰稿的正确打开方式(请认真阅读后再操作！)</b></h4>
                  						<p style="font-size: 16px;">第一步：您需要认真填写公文标题，<b>公文标题必须填写且不能重复</b>，如果您填写的公文标题重复，您将必须修改标题，否则系统将不允许您提交。</p>
                  						<p style="font-size: 16px;">第二步：您需要选择公文接收人，您选择的公文接收人才可以看到公文的内容和下载附件。注意：<b>不可以只选择机构名称而不选择接收人姓名</b>，您必须选中至少一个接收人姓名，否则系统将不允许您提交公文。</p>
                  						<p style="font-size: 16px;">第三步：您需要选择公文审核人，公文审核人一旦选择后就无法修改。</p>
                  						<p style="font-size: 16px;">第四步：您需要上传公文的电子文档，仅支持Word或者PDF格式的电子文档。</p>
                  						<p style="font-size: 16px;">第五步：如果公文本身携带了附件，请上传附件。附件仅支持常见的文档（包括Word、Excel、PPT、PDF四种文档）和RAR或ZIP格式压缩包，其它类型的文件不允许上传，如果确实需要上传其他文件，请将其打包压缩后再上传！</p>
                                    </div>
									
									<section class="panel panel-default">
	                                	<header class="panel-heading font-bold" style="font-size: 17px;">
	                                		1.填写公文标题<font color="red">（必填，唯一）</font>
	                                	</header>
		                                <div class="panel-body" style="padding-left: 0px;">
		                                	<div class="col-sm-4">
												<input type="text" name="title" class="form-control" 
													data-required="true" placeholder="请输入公文标题..."
													data-notblank="true" data-rangelength="[2,150]">
											</div>
	            						</div>
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold" style="font-size: 17px;">
	                                		2.确定公文接收人<font color="red">（必填）</font>
	                                	</header>
		                                <div class="panel-body">
		                                	
		                                	<h4>注意：您必须选中具体的接收人姓名才可以，不能只选择机构！</h4>
		                                	
											<div class="row">
												<div class="col-sm-4">
													<h4>搜索联系人</h4>
													<div class="form-group col-sm-8">
														<label for="input-check-node" class="sr-only">搜索联系人:</label>
														<input type="text" name="search" class="form-control" id="input-check-node" placeholder="请输入联系人姓名..." 
															value="">
													</div>
													
													<div class="form-group row">
														<div class="col-sm-8">
															<button type="button" class="btn btn-primary check-node" id="btn-check-node">选中</button>
															<button type="button" class="btn btn-danger check-node" id="btn-uncheck-node">反选</button>
															<button type="button" class="btn btn-success check-node" id="btn-toggle-checked">点击</button>
														</div>
													</div>
													
													<div class="form-group row">
														<div class="col-sm-8">
															<button type="button" class="btn btn-primary" id="btn-check-all">全选中</button>
															<button type="button" class="btn btn-danger" id="btn-uncheck-all">全取消</button>
														</div>
													</div>
													
													
												</div>
												
												<div class="col-sm-4">
													<h4>联系人列表</h4>
													<div id="treeview-checkable" class="" style="height: 300px; overflow:auto;">
														
													</div>
													
													<!-- 设置选中联系人之后提交表单用的字段名称 -->
													<script type="text/javascript">
														var checkBoxName = "received";
													</script>
												</div>
												
												<div class="col-sm-4">
													<h4>您选中的联系人</h4>
													<div id="checkable-output" style="height: 300px; overflow:auto;">
														<table id="people"></table>
													</div>
												</div>
											</div>
											
	            						</div>
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold" style="font-size: 17px;">
	                                		3.选择公文审核人<font color="red">（必选）</font>
	                                	</header>
		                                <div class="panel-body" style="padding-left: 0px;">
		                                	<div class="col-sm-4">
												<select name="auditor" class="form-control m-b">
													<c:forEach items="${auditors}" var="auditor">
														<option value="${auditor.userid}">${auditor.usertruename}</option>
													</c:forEach>
                                                </select>
											</div>
	            						</div>
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold" style="font-size: 17px;">
	                                		4.上传公文电子版文档<font color="red">（必须上传）</font>
	                                	</header>
		                                <div class="panel-body">
											<input id="document" type="file" data-required="true" reqdata-icon="false" name="doc"
			                                        data-classButton="btn btn-default" data-classInput="form-control inline input-s"/>
	            						</div>
	            					</section>
	            					
	            					<section class="panel panel-default">
	                                	<header class="panel-heading font-bold" style="font-size: 17px;">
	                                		5.上传公文附件（非必填，如果没有请留空）
	                                	</header>
		                                <div class="panel-body">
											<a class="btn btn-primary" onclick="appendAttachment();">增加附件</a>
												
											<!-- 设置附件字段名称，以便更改,注意要和下面表格中文件选择框的name保持一致 -->
											<script type="text/javascript">
												var attachmentName = "attachment";
											</script>
											<br><br />
											
											<table id="attachments">
												<tr>
													<td><input type="file" style="margin: 5px;" name="attachment" data-trigger="change" data-icon="false" data-classButton="btn btn-default" data-classInput="inline input-s"/></td>
													<td><button class="btn btn-danger " style="margin: 5px;" onclick="removeAttachment(this);">删除</button></td>
												</tr>
											</table>
	            						</div>
	            					</section>
	            				
	            				</form>
								
							</section>
						</section>
						<a href="#" class="hide nav-off-screen-block" data-toggle="class:nav-off-screen" data-target="#nav"></a>
					</section>
					
					<aside class="bg-light lter b-l aside-md hide" id="notes">
						<div class="wrapper">Notification</div>
					</aside>
				</section>
			</section>
		</section>
		<!-- Bootstrap -->
		
		<script type="text/javascript" src="<%= basePath %>js/fuelux/fuelux.js"></script>
		<script type="text/javascript" src="<%= basePath %>js/parsley/parsley.min.js"></script>
		<script type="text/javascript" src="<%= basePath %>js/file-input/bootstrap-filestyle.min.js"></script>
		
		<script>
			var innerHtml = "<tr><td><input type=\"file\" name=\"" + attachmentName 
				+ "\" style=\"margin: 5px;\" data-trigger=\"change\" data-icon=\"false\" data-classButton=\"btn btn-default\" data-classInput=\"form-control inline input-s\"/></td><td><button class=\"btn btn-danger \" style=\"margin: 5px;\" onclick=\"removeAttachment(this);\">删除</button></td></tr>";
			
			//增加附件
			function appendAttachment(){
				
				//找到附件上传组件所在div,在后面追加一个附件上传框
				$("#attachments").append(innerHtml);
				
				//阻止按钮的默认行为
				return false;
			}
			
			//删除附件
			function removeAttachment(thisComponent) {
				//移除当前上传框
				$(thisComponent).parent().parent().remove(); 
				
				//阻止按钮的默认行为
				return false;
			}
			
			//校验的输入是否正确
			function validate() {
				
				//检查一下联系人是否选到就可以了，其他的输入框有专门的校验组件负责
				var selectedPeople = $("input[name='" + checkBoxName + "']");
				
				if(selectedPeople.length == 0) {  //用户可能既没有勾选机构也没勾选联系人，也有可能只勾选了机构，没选联系人
					alert("您还没有选取联系人，请选择至少一位联系人之后再提交公文！");
					return false;
				}
				
				return true;
			}
		</script>
		
		<script type="text/javascript">
			$(function() {
				//递归获取所有的结点id
				function getNodeIdArr( node ){
				        var ts = [];
				        if(node.nodes){
				            for(x in node.nodes){
				                ts.push(node.nodes[x].nodeId)
				                if(node.nodes[x].nodes){
				                var getNodeDieDai = getNodeIdArr(node.nodes[x]);
				                    for(j in getNodeDieDai){
				                        ts.push(getNodeDieDai[j]);
				                    }
				                }
				            }
				        }else{
				            ts.push(node.nodeId);
				       }
				   return ts;
				}

				function getData(){
					var url = "<%= basePath %>" + "article/getTree";
					var jsondata;
					$.ajax({ 
	                    type: "post", 
	                    url: url, 
	                    dataType: "json", 
	                    async: false,
	                    success: function (data) { 
	                    	jsondata = JSON.stringify(data);
	                    }
	            	});
					return jsondata;
				}
				
				var $checkableTree = $('#treeview-checkable').treeview({
					data: getData(),
					showIcon: false,
					showCheckbox: true,
					onNodeChecked: function (event, node) {  //选中事件触发

						if(undefined == node.id){
							appentContent = '<tr id=\"tr' + node.text + '\"><td>您选中了' + node.text + '</td></tr>';
						} else {
							appentContent = '<tr id=\"tr' + node.text + '\"><td>您选中了' + node.text + '<input type=\"hidden\" name=\"' + checkBoxName + '\" value=\"' + node.id + '\"/></td></tr>';
						}
						
						$('#people').append(appentContent);
						
						//选中父节点之后级联选中下面的所有子节点
						var selectNodes = getNodeIdArr(node);   //获取所有子节点
			            if(selectNodes){   //子节点不为空，则选中所有子节点
			                $('#treeview-checkable').treeview('checkNode', [ selectNodes,{ silent: false }]);
			            }
					},
					onNodeUnchecked: function(event, node) {  //取消选中事件触发
						var id = "#tr" + node.text;
						$(id).remove();
						
						//取消父节点之后级联取消所有子节点
						var selectNodes = getNodeIdArr(node);//获取所有子节点
			            if(selectNodes){ //子节点不为空，则取消选中所有子节点
			                $('#treeview-checkable').treeview('uncheckNode', [ selectNodes,{ silent: false }]);
			            }
					}
				});
			
				var findCheckableNodess = function() {
					return $checkableTree.treeview('search', [$('#input-check-node').val(), {
						ignoreCase: false,
						exactMatch: false
					}]);
				};
				var checkableNodes = findCheckableNodess();
			
				// Check/uncheck/toggle nodes
				$('#input-check-node').on('keyup', function(e) {
					checkableNodes = findCheckableNodess();
					$('.check-node').prop('disabled', !(checkableNodes.length >= 1));
				});
			
				$('#btn-check-node.check-node').on('click', function(e) {
					$checkableTree.treeview('checkNode', [checkableNodes, {
						silent: $('#chk-check-silent').is(':checked')
					}]);
				});
			
				$('#btn-uncheck-node.check-node').on('click', function(e) {
					$checkableTree.treeview('uncheckNode', [checkableNodes, {
						silent: $('#chk-check-silent').is(':checked')
					}]);
				});
			
				$('#btn-toggle-checked.check-node').on('click', function(e) {
					$checkableTree.treeview('toggleNodeChecked', [checkableNodes, {
						silent: $('#chk-check-silent').is(':checked')
					}]);
				});
			
				// Check/uncheck all
				$('#btn-check-all').on('click', function(e) {
					$checkableTree.treeview('checkAll', {
						silent: $('#chk-check-silent').is(':checked')
					});
				});
			
				$('#btn-uncheck-all').on('click', function(e) {
					$checkableTree.treeview('uncheckAll', {
						silent: $('#chk-check-silent').is(':checked')
					});
				});

			});
		</script>

	</body>

</html>
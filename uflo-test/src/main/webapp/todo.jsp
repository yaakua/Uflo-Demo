<%@page import="java.util.UUID"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootbox.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>处理待办</title>
<style type="text/css">
	body{
		margin: 5px;
	}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">
			<a href="#todo" data-toggle="tab">待处理</a>
		</li>
		<li><a href="#history" data-toggle="tab">已完成</a></li>
	</ul>
	<div class="tab-content">
    	<div class="tab-pane fade in active" id="todo">
    		<table class="table table-bordered" id="todoTable">
    			<thead>
    				<tr>
    					<td style="width:290px">申请件编号</td>
    					<td>门店</td>
    					<td style="width:120px">分派日期</td>
    					<td style="width:100px">质检人员</td>
    					<td style="width:200px">操作</td>
    				</tr>
    			</thead>
    			<tbody>
    			</tbody>
    		</table>
    	</div>
    	<div class="tab-pane fade" id="history">
    		<table class="table table-bordered" id="historyTable">
    			<thead>
    				<tr>
    					<td style="width:290px">申请件编号</td>
    					<td>门店</td>
    					<td style="width:120px">分派日期</td>
    					<td style="width:100px">质检人员</td>
    					<td style="width:200px">操作</td>
    				</tr>
    			</thead>
    			<tbody>
    			</tbody>
    		</table>
    	</div>
    </div>

<script type="text/javascript">
$.ajax({
	url:'poc?method=loadTodo',
	success:function(data){
		var todoBody=$("#todoTable").find("tbody");
		for(var i=0;i<data.length;i++){
			var item=data[i];
			var tr=$("<tr></tr>");
			todoBody.append(tr);
			tr.append("<td>"+item.id+"</td>");
			tr.append("<td>"+item.store+"</td>");
			tr.append("<td>"+item.assignDate+"</td>");
			tr.append("<td>"+item.inspector+"</td>");
			var td=$("<td></td>");
			tr.append(td);
			if(item.taskId){
				var url=item.url+"?businessId="+item.id+"&taskId="+item.taskId+"&taskName="+item.taskName;
				url=encodeURI(encodeURI(url));
				var processTask=$("<span><a href='"+url+"'>处理</a></span>");
				td.append(processTask);
			}
			var detail=$("<span style='margin-left:5px'><a href='detail.jsp?businessId="+item.id+"' target='_blank'>详情</a></span>")
			td.append(detail);
			
			var url="uflo/diagram?processInstanceId="+item.processInstanceId;
			var processImage=$("<span style='margin-left:5px'><a href='"+url+"'>查看流程图</a></span>");
			td.append(processImage);
			
			todoBody.append(tr);
		}
	}
});
$.ajax({
	url:'poc?method=loadHistory',
	success:function(data){
		var todoBody=$("#historyTable").find("tbody");
		for(var i=0;i<data.length;i++){
			var item=data[i];
			var tr=$("<tr></tr>");
			todoBody.append(tr);
			tr.append("<td>"+item.id+"</td>");
			tr.append("<td>"+item.store+"</td>");
			tr.append("<td>"+item.assignDate+"</td>");
			tr.append("<td>"+item.inspector+"</td>");
			var td=$("<td></td>");
			tr.append(td);
			var detail=$("<span style='margin-left:5px'><a href='detail.jsp?businessId="+item.id+"' target='_blank'>详情</a></span>")
			td.append(detail);
			todoBody.append(tr);
		}
	}
});
</script>
</body>
</html>
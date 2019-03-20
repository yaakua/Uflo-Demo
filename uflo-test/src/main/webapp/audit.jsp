<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootbox.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<title>审核</title>
<style type="text/css">
	body{
		margin: 10px;
	}
</style>
</head>
<body>

<% 
String taskName=request.getParameter("taskName");
taskName=URLDecoder.decode(URLDecoder.decode(taskName,"utf-8"),"utf-8");
if(!taskName.startsWith("任务1:质检")){	
%>
	<div class="panel panel-default">
	    <div class="panel-heading">
	        <h3 class="panel-title">
	           	反馈结果：
	        </h3>
	    </div>
	    <div class="panel-body">
	    	<table class="table table-bordered" id="feedbackTable">
	    		<thead>
	    			<tr>
	    				<td style="width:120px">反馈人</td>
	    				<td style="width:100px">反馈结果</td>
	    				<td>描述</td>
	    			</tr>
	    		</thead>
	    		<tbody>
	    		</tbody>
	    	</table>
	    </div>
	</div>
	<div class="panel panel-default">
	    <div class="panel-body">
	    	<div>
		    	<label class="checkbox-inline">
						<input type="radio" name="approve" checked="checked" value="confirm" onchange="change()"> 确认：
						<input type="text" class="form-control" id="confirm_opinion" style="display:inline-block;width:400px">
				</label>	
	    	</div>
	    	<div>
		    	<label class="checkbox-inline" style="margin-top:15px">
						<input type="radio" name="approve" value="dispute" onchange="change()"> 争议：
						<input type="text" class="form-control" id="dispute_opinion" readonly="readonly"  style="display:inline-block;width:400px">
				</label>	
	    	</div>
	    </div>
	</div>
	<div class="panel panel-default">
	    <div class="panel-body">
	    	<button type="button" id="submitTask" class="btn btn-primary">提交</button>
	    </div>
	</div>
	<script type="text/javascript">
		var businessId="<%=request.getParameter("businessId")%>";
		$.ajax({
			type:'POST',
			url:'poc?method=loadOpinions',
			data:{itemId:businessId},
			success:function(opinions){
				var body=$("#feedbackTable").find("tbody");
				for(var i=0;i<opinions.length;i++){
					var op=opinions[i];
					var tr=$("<tr></tr>");
					body.append(tr);
					tr.append("<td>"+op.user+"</td>");
					tr.append("<td>"+(op.feedbackType==='confirm' ? '确认' : '争议')+"</td>");
					tr.append("<td>"+op.desc+"</td>");
				}
			}
		});
	
		function change(){
			var result=$("input[name='approve']:checked").val();
			if(result==='confirm'){
				$("#confirm_opinion").prop("readonly",'');
				$("#dispute_opinion").prop("readonly",'readonly');
			}else{
				$("#confirm_opinion").prop("readonly",'readonly');
				$("#dispute_opinion").prop("readonly",'');
			}
		}
	
		$("#submitTask").click(function(){
			var feedbackType=$("input[name='approve']:checked").val();
			var opinion=$("#dispute_opinion").val();
			
			if(feedbackType==='confirm'){
				opinion=$("#confirm_opinion").val();
			}
			if(!opinion || opinion.length<1){
				bootbox.alert("请输入描述！");
				return;
			}
			var taskId=<%=request.getParameter("taskId")%>;
			var taskName="<%=taskName%>";
			bootbox.confirm("真的要提交？",function(result){
				if(!result)return;
				$.ajax({
					type:'POST',
					data:{feedbackType:feedbackType,businessId:businessId,desc:opinion,taskId:taskId,taskName:taskName},
					url:'poc?method=completeTask',
					success:function(){
						window.open("todo.jsp","_self")
					}
				});
			});
			
		});
	</script>
<%
}else{
%>
<div class="panel panel-default">
    <div class="panel-body">
    	<button type="button" id="firstSubmit" class="btn btn-primary">提交</button>
    </div>
</div>
<script type="text/javascript">
	$("#firstSubmit").click(function(){
		bootbox.confirm("真的要提交？",function(result){
			if(!result){
				return;
			}
			var taskId=<%=request.getParameter("taskId")%>;
			$.ajax({
				type:'POST',
				url:'poc?method=firstSubmit',
				data:{taskId:taskId,path:'提交'},
				success:function(){
					window.open('todo.jsp',"_self");
				}
			})
		});
	});
	$("#applyCheckButton").click(function(){
		bootbox.confirm("真的要申请复核？",function(result){
			if(!result){
				return;
			}
			var taskId=<%=request.getParameter("taskId")%>;
			$.ajax({
				type:'POST',
				url:'poc?method=firstSubmit',
				data:{taskId:taskId,path:'申请复核'},
				success:function(){
					window.open('todo.jsp',"_self");
				}
			})
		});
	});
</script>
<% 
}
%>
</body>
</html>
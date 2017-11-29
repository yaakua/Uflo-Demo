<%@page import="java.util.UUID"%>
<%@page import="java.util.Date"%>
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
<title>开启流程</title>
<style type="text/css">
	body{
		margin: 10px;
	}
</style>
</head>
<body>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">
           	开启申请件流程
        </h3>
    </div>
    <div class="panel-body">
    	<div>
	        <span>申请件编号：</span>
	        <span>
	        	<input type="text" class="form-control" id="id" value="<%=UUID.randomUUID().toString() %>" style="display:inline-block;width:300px">
	        </span>
    	</div>
    	<div style="margin-top: 20px">
	        <span>门店：</span>
	        <span>
	        	<input type="text" class="form-control" id="store" value="上海浦东大道营业部" style="display:inline-block;width:300px">
	        </span>
    	</div>
    	<div style="margin-top: 20px">
	        <span>指定质检人员：</span>
	        <span>
	        	<select class="form-control" id="inspector" style="display:inline-block;width:300px">
	        		<option>user1</option>
	        		<option>user2</option>
	        		<option>user3</option>
	        	</select>
	        </span>
    	</div>
    </div>
    <div class="panel-footer" style="background: #fdfdfd">
    	<button type="button" class="btn btn-default" id="startBtn">开启流程</button>
    </div>
</div>
<script type="text/javascript">
	$("#startBtn").click(function(){
		bootbox.confirm("真的要开启？",function(result){
			if(!result)return;
			var id=$("#id").val();
			var store=$("#store").val();
			var inspector=$("#inspector").val();
			
			$.ajax({
				url:"poc?method=startProcess",
				type:'POST',
				data:{id:id,store:store,inspector:inspector},
				success:function(){
					$("#startBtn").addClass("disabled");
					bootbox.confirm("开启流程操作成功,是否要跳转到待办页面？",function(){
						window.open("todo.jsp","_self");
					});
				},
				error:function(){
					alert("操作失败！");
				}
			});
		});
	});
</script>
</body>
</html>
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
<title>明细</title>
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
	</script>
</body>
</html>
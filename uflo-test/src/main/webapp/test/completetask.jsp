<%@page import="com.bstek.uflo.service.TaskService"%>
<%@page import="com.bstek.uflo.service.StartProcessInfo"%>
<%@page import="com.bstek.uflo.service.ProcessService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.bstek.uflo.utils.EnvironmentUtils"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>complete task</title>
</head>
<body>
	<%
		EnvironmentUtils utils=EnvironmentUtils.getEnvironment();
		ApplicationContext context=utils.getApplicationContext();
		TaskService ps=(TaskService)context.getBean(TaskService.BEAN_ID);
		String taskId=request.getParameter("taskId");
		if(taskId==null){
			throw new RuntimeException("taskId cannot be null.");
		}
		ps.start(Long.valueOf(taskId));
		ps.complete(Long.valueOf(taskId));
	
	%>
</body>
</html>
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
<title>StartProcess</title>
</head>
<body>
	<%
		EnvironmentUtils utils=EnvironmentUtils.getEnvironment();
		ApplicationContext context=utils.getApplicationContext();
		ProcessService ps=(ProcessService)context.getBean(ProcessService.BEAN_ID);
		StartProcessInfo info=new StartProcessInfo();
		info.setPromoter(utils.getLoginUser());
		info.setCompleteStartTask(true);
		info.setBusinessId("bus-001");
		ps.startProcessByName("flow-demo", info);
	
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <title>Struts2 Message</title>
</head>
<body>
<div style="margin-top: 30px; text-align: center;">
    <s:text name="custom.message.username"/>
    <input type="text">
    <br/>
    <s:text name="custom.message.password"/>
    <input type="password">
</div>
</body>
</html>

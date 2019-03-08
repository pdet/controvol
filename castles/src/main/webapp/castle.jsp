<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>
<body>
<a href="<%=request.getContextPath()%>/castle.jsp">home</a>
<%
String guestbookName = request.getParameter("guestbookName");
if (guestbookName == null) {
guestbookName = "default";
}
pageContext.setAttribute("guestbookName", guestbookName);
%>
<p>Player '${fn:escapeXml(guestbookName)}'</p>


<!--<form action="/go" method="post">
    <div><textarea name="content" rows="1" cols="20"></textarea></div>
     <button type="submit" name="action" value="add">add</button>
  	<button type="submit" name="action" value="query">query</button>
    <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
</form>-->

<form action="/go" method="post">
	Player Name: <input type="text" name="name">
<br />
	Rank Level: <input type="number" name="level" />
    <button type="submit" name="action" value="add">put</button>
  	<button type="submit" name="action" value="query">get</button>
 </form>



</body>
</html>
<%-- //[END all]--%>
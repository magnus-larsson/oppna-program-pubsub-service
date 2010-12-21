<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
		<title>Administration - PubSub</title>
		
		<link rel="shortcut icon" href="http://www.vgregion.se/VGRimages/favicon.ico" type="image/x-icon" />
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/typography.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/forms.css" type="text/css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/pubsub.css" type="text/css" />

	</head>
	<body>
		<h2>PubSubHubbub prenumeranter</h2>
		
		<form action='' method="post">
			<table>
				<tr>
					<th>Topic</th>
					<th>Callback</th>
					<th></th>
				</tr>
				<c:forEach var="pushSubscriber" items="${pushSubscribers}">
					<tr>
						<td>${pushSubscriber.topic}</td>
						<td>${pushSubscriber.callback}</td>
						<td><!--<input type="submit" value="Ta bort" name="delete-${pushSubscriber.id}">--></td>
					</tr>
				</c:forEach>
			</table>
		</form>
		<div><a href="${pageContext.request.contextPath}/admin/new"><img src="${pageContext.request.contextPath}/resources/img/page_add.png" /> Skapa ny prenumerant</a></div>
	</body>
</html>
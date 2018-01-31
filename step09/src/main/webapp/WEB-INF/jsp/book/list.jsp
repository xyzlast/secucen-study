<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <link href="/node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="/node_modules/jquery/dist/jquery.min.js"></script>
    <script src="/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body class="container">
    <h2>jstl test code 입니다.</h2>
    <div class="example-form">
    <h3>c:forEach example code</h3>
    <table class="table table-striped table-bordered table-hover">
        <thead>
            <tr>
                <th>이름</th>
                <th>상태</th>
                <th>저자</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="book" items="${books}">
                <tr>
                    <td>${book.name}</td>
                    <c:set var="status" value="일반" />
                    <c:choose>
                        <c:when test="${book.status eq 'NORMAL'}">
                            <c:set var="status" value="일반" />
                        </c:when>
                        <c:when test="${book.status eq 'RENTNOW'}">
                            <c:set var="status" value="대여중" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="status" value="분실중" />
                        </c:otherwise>
                    </c:choose>
                    <td>${status}</td>
                    <td>${book.author}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="example-form">
        <fieldset>
            <legend>FMT examples - Date</legend>
            <ul>
                <li><fmt:formatDate value="${date}" type="DATE" pattern="yyyy/MM/dd" /></li>
                <li><fmt:formatDate value="${date}" type="DATE" pattern="yyyy년 M월 dd일" /></li>
            </ul>
        </fieldset>
        <br />
        <fieldset>
            <legend>FMT example - Number</legend>
            <ul>
                <li>orginal : ${number}</li>
                <li><fmt:formatNumber value="${number}" groupingUsed="true" currencySymbol=","/></li>
                <li><fmt:formatNumber value="${number}" minFractionDigits="5"/></li>
                <li><fmt:formatNumber value="${number}" type="CURRENCY"/></li>
                <li><fmt:formatNumber value="234.3" pattern="△#,##0.00; ▼#,##0.00" /></li>
                <li><fmt:formatNumber value="-1234.56" pattern="△#,##0.00; ▼#,##0.00" /></li>
                <li><fmt:formatNumber value="0.99" type="percent"/></li>
            </ul>
        </fieldset>
    </div>
</div>
</body>
</html>

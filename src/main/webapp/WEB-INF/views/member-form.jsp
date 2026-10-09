<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${formAction} - Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

<div class="page-wrapper">

    <nav class="navbar">
        <span class="nav-title">Library Management System</span>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/members">Members</a>
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </div>
    </nav>

    <div class="content">
        <h2>${formAction}</h2>

        <!-- Determine form action URL based on whether this is add or edit -->
        <c:choose>
            <c:when test="${member.id != 0}">
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/members/edit" />
            </c:when>
            <c:otherwise>
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/members/add" />
            </c:otherwise>
        </c:choose>

        <form action="${actionUrl}" method="post" class="form-card">

            <!-- Hidden ID for edit -->
            <input type="hidden" name="id" value="${member.id}" />

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${member.name}" required />
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${member.email}" required />
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password"
                       value="${member.password}" required />
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${formAction}</button>
                <a href="${pageContext.request.contextPath}/members" class="btn btn-secondary">Cancel</a>
            </div>

        </form>
    </div>
</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Members - Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

<div class="page-wrapper">

    <nav class="navbar">
        <span class="nav-title">Library Management System</span>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </div>
    </nav>

    <div class="content">
        <h2>Members &amp; Librarians</h2>

        <a href="${pageContext.request.contextPath}/members/add" class="btn btn-primary">+ Add Member / Librarian</a>

        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="member" items="${members}">
                    <tr>
                        <td>${member.id}</td>
                        <td>${member.name}</td>
                        <td>${member.email}</td>
                        <td>${member.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/members/edit/${member.id}"
                               class="btn btn-small btn-secondary">Edit</a>

                            <a href="${pageContext.request.contextPath}/members/delete/${member.id}"
                               class="btn btn-small btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty members}">
                    <tr>
                        <td colspan="5" class="no-data">No members found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>

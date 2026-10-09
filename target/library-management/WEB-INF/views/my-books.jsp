<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Books - Library Management System</title>
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
        <h2>My Borrowing History</h2>
        <p>Showing books borrowed by: <strong>${sessionScope.userName}</strong></p>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Book Title</th>
                    <th>Author</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                    <th>Fine</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${history}">
                    <tr>
                        <td>${record.book.title}</td>
                        <td>${record.book.author}</td>
                        <td><fmt:formatDate value="${record.issueDate}" pattern="dd-MM-yyyy" /></td>
                        <td><fmt:formatDate value="${record.dueDate}" pattern="dd-MM-yyyy" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty record.returnDate}">
                                    <fmt:formatDate value="${record.returnDate}" pattern="dd-MM-yyyy" />
                                </c:when>
                                <c:otherwise>Not returned yet</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span class="status-${record.status}">${record.status}</span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${record.fine > 0}">Rs. ${record.fine}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty history}">
                    <tr>
                        <td colspan="7" class="no-data">You have not borrowed any books yet.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>

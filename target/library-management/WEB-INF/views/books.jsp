<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Books - Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

<div class="page-wrapper">

    <!-- Navigation Bar -->
    <nav class="navbar">
        <span class="nav-title">Library Management System</span>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </div>
    </nav>

    <div class="content">
        <h2>Books</h2>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <!-- Search form (all roles) -->
        <form action="${pageContext.request.contextPath}/books/search" method="get" class="search-form">
            <input type="text" name="keyword" placeholder="Search by title..." value="${keyword}" />
            <button type="submit" class="btn btn-secondary">Search</button>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Show All</a>
        </form>

        <!-- Add Book button (ADMIN and LIBRARIAN only) -->
        <c:if test="${sessionScope.role == 'ADMIN' or sessionScope.role == 'LIBRARIAN'}">
            <a href="${pageContext.request.contextPath}/books/add" class="btn btn-primary">+ Add Book</a>
        </c:if>

        <!-- Books Table -->
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>ISBN</th>
                    <th>Category</th>
                    <th>Total</th>
                    <th>Available</th>
                    <!-- Show Actions column only if not a plain MEMBER -->
                    <c:if test="${sessionScope.role != 'MEMBER'}">
                        <th>Actions</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${books}">
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.isbn}</td>
                        <td>${book.category}</td>
                        <td>${book.quantity}</td>
                        <td>${book.availableQuantity}</td>

                        <c:if test="${sessionScope.role != 'MEMBER'}">
                            <td>
                                <!-- Edit: ADMIN and LIBRARIAN -->
                                <a href="${pageContext.request.contextPath}/books/edit/${book.id}"
                                   class="btn btn-small btn-secondary">Edit</a>

                                <!-- Delete: ADMIN only -->
                                <c:if test="${sessionScope.role == 'ADMIN'}">
                                    <a href="${pageContext.request.contextPath}/books/delete/${book.id}"
                                       class="btn btn-small btn-danger">Delete</a>
                                </c:if>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>

                <!-- No books found message -->
                <c:if test="${empty books}">
                    <tr>
                        <td colspan="8" class="no-data">No books found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div>
</div>

</body>
</html>

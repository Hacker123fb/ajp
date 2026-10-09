<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

<div class="page-wrapper">

    <!-- Navigation Bar -->
    <nav class="navbar">
        <span class="nav-title">Library Management System</span>
        <div class="nav-links">
            <span>Welcome, ${sessionScope.userName} (${sessionScope.role})</span>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="content">
        <h2>Dashboard</h2>
        <p>Welcome to the Library Management System. Use the links below to navigate.</p>

        <div class="card-grid">

            <!-- All roles can view books -->
            <div class="card">
                <h3>Books</h3>
                <p>Browse and search the book catalog.</p>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">View Books</a>
            </div>

            <!-- ADMIN and LIBRARIAN: Issue / Return -->
            <c:if test="${sessionScope.role == 'ADMIN' or sessionScope.role == 'LIBRARIAN'}">
                <div class="card">
                    <h3>Issue / Return</h3>
                    <p>Issue books to members or process returns.</p>
                    <a href="${pageContext.request.contextPath}/borrow" class="btn btn-primary">Manage Borrowing</a>
                </div>
            </c:if>

            <!-- ADMIN only: Manage Members -->
            <c:if test="${sessionScope.role == 'ADMIN'}">
                <div class="card">
                    <h3>Members</h3>
                    <p>Add, edit, or remove library members.</p>
                    <a href="${pageContext.request.contextPath}/members" class="btn btn-primary">Manage Members</a>
                </div>
            </c:if>

            <!-- MEMBER: My Books -->
            <c:if test="${sessionScope.role == 'MEMBER'}">
                <div class="card">
                    <h3>My Books</h3>
                    <p>View your borrowing history.</p>
                    <a href="${pageContext.request.contextPath}/borrow/history" class="btn btn-primary">My Books</a>
                </div>
            </c:if>

        </div>
    </div>

</div>

</body>
</html>

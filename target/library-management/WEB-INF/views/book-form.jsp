<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!-- formAction is set by the controller: "Add Book" or "Edit Book" -->
    <title>${formAction} - Library Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>

<div class="page-wrapper">

    <nav class="navbar">
        <span class="nav-title">Library Management System</span>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/books">Books</a>
            <a href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
        </div>
    </nav>

    <div class="content">
        <h2>${formAction}</h2>

        <!--
            This form is used for BOTH Add Book and Edit Book.
            - Add:  action="/books/add"   (book.id will be 0)
            - Edit: action="/books/edit"  (book.id will be the existing ID)

            The controller sets "formAction" and the "book" model attribute.
        -->
        <c:choose>
            <c:when test="${book.id != 0}">
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/books/edit" />
            </c:when>
            <c:otherwise>
                <c:set var="actionUrl" value="${pageContext.request.contextPath}/books/add" />
            </c:otherwise>
        </c:choose>

        <form action="${actionUrl}" method="post" class="form-card">

            <!-- Hidden ID field for edit -->
            <input type="hidden" name="id" value="${book.id}" />

            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${book.title}" required />
            </div>

            <div class="form-group">
                <label for="author">Author:</label>
                <input type="text" id="author" name="author" value="${book.author}" required />
            </div>

            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" value="${book.isbn}" />
            </div>

            <div class="form-group">
                <label for="category">Category:</label>
                <input type="text" id="category" name="category" value="${book.category}" />
            </div>

            <div class="form-group">
                <label for="quantity">Total Quantity:</label>
                <input type="number" id="quantity" name="quantity" value="${book.quantity}" min="1" required />
            </div>

            <!-- availableQuantity is automatically set by BookService for new books -->
            <!-- For edit, allow manual correction -->
            <c:if test="${book.id != 0}">
                <div class="form-group">
                    <label for="availableQuantity">Available Quantity:</label>
                    <input type="number" id="availableQuantity" name="availableQuantity"
                           value="${book.availableQuantity}" min="0" required />
                </div>
            </c:if>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${formAction}</button>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
            </div>

        </form>
    </div>
</div>

</body>
</html>

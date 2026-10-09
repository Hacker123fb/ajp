<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Borrow Management - Library Management System</title>
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
        <h2>Borrow Management</h2>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <!-- ===== Issue Book Form ===== -->
        <div class="form-card">
            <h3>Issue a Book</h3>
            <form action="${pageContext.request.contextPath}/borrow/issue" method="post">

                <div class="form-group">
                    <label for="memberId">Select Member:</label>
                    <select id="memberId" name="memberId" required>
                        <option value="">-- Select Member --</option>
                        <c:forEach var="member" items="${members}">
                            <option value="${member.id}">${member.name} (${member.email})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="bookId">Select Book:</label>
                    <select id="bookId" name="bookId" required>
                        <option value="">-- Select Book --</option>
                        <c:forEach var="book" items="${books}">
                            <option value="${book.id}">
                                ${book.title} - ${book.author}
                                (Available: ${book.availableQuantity})
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dueDate">Due Date:</label>
                    <input type="date" id="dueDate" name="dueDate" required />
                </div>

                <button type="submit" class="btn btn-primary">Issue Book</button>
            </form>
        </div>

        <!-- ===== Borrow Records Table ===== -->
        <h3>Borrow Records</h3>
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Member</th>
                    <th>Book</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Return Date</th>
                    <th>Status</th>
                    <th>Fine</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${records}">
                    <tr>
                        <td>${record.id}</td>
                        <td>${record.user.name}</td>
                        <td>${record.book.title}</td>
                        <td><fmt:formatDate value="${record.issueDate}" pattern="dd-MM-yyyy" /></td>
                        <td><fmt:formatDate value="${record.dueDate}" pattern="dd-MM-yyyy" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty record.returnDate}">
                                    <fmt:formatDate value="${record.returnDate}" pattern="dd-MM-yyyy" />
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span class="status-${record.status}">${record.status}</span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${record.fine > 0}">
                                    Rs. ${record.fine}
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <!-- Return button shown only for ISSUED records -->
                            <c:if test="${record.status == 'ISSUED'}">
                                <a href="${pageContext.request.contextPath}/borrow/return/${record.id}"
                                   class="btn btn-small btn-secondary">Return</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty records}">
                    <tr>
                        <td colspan="9" class="no-data">No borrow records found.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>

    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var today = new Date().toISOString().split("T")[0];
        var dueDateInput = document.getElementById("dueDate");
        if (dueDateInput) {
            dueDateInput.min = today;
        }
    });
</script>
</body>
</html>

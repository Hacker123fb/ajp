<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error - Library Management System</title>
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
        <div class="error-box">
            <h2>Access Denied</h2>
            <p>You do not have permission to access this page.</p>
            <p>Please contact your administrator if you believe this is a mistake.</p>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">Back to Dashboard</a>
        </div>
    </div>

</div>

</body>
</html>

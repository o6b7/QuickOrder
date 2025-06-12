<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome to QuickOrder</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        .container {
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
        }

        .container img {
            max-width: 150px;
            margin-bottom: 20px;
        }

        h1 {
            font-size: 2rem;
            color: #6B4532;
            margin-bottom: 10px;
        }

        p {
            font-size: 1rem;
            color: #333;
        }

        /* Adjust navbar to take 100% width */
        .nav-bar {
            width: 100%;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />

    <!-- Include the appropriate navigation bar based on user role -->
    <c:choose>
        <c:when test="${sessionScope.userRole == 'Customer'}">
            <jsp:include page="customerNav.jsp" />
        </c:when>
        <c:when test="${sessionScope.userRole == 'Managing Staff'}">
            <jsp:include page="managerNav.jsp" />
        </c:when>
        <c:otherwise>
            <jsp:include page="driverNav.jsp" />
        </c:otherwise>
    </c:choose>

    <!-- Welcome Container -->
    <div class="container">
        <img src="images/logo.png" alt="QuickOrder Logo">
        <h1>Welcome to QuickOrder</h1>
        <p>You can navigate using the above navigation bar.</p>
    </div>
</body>
</html>

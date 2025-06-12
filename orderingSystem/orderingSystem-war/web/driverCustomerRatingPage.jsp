<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Ratings for Driver</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: auto;
            margin-top: 50px;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }

        .empty-message {
            text-align: center;
            color: gray;
            font-size: 18px;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="driverNav.jsp" />
    <div class="container">
        <h1>Customer Ratings</h1>
        
        <c:if test="${empty feedbackList}">
            <p class="empty-message">No feedback available.</p>
        </c:if>

        <c:if test="${not empty feedbackList}">
            <table>
                <thead>
                    <tr>
                        <th>Rating</th>
                        <th>Feedback Content</th>
                        <th>Feedback Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="feedback" items="${feedbackList}">
                        <tr>
                            <td>${feedback.rating}</td>
                            <td>${feedback.content}</td>
                            <td>${feedback.date}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>

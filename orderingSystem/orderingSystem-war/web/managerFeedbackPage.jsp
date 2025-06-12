<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manager Feedback Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 90%;
            margin: auto;
            margin-top: 30px;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        .search-bar {
            text-align: center;
            margin-bottom: 20px;
        }

        .search-bar input {
            padding: 10px;
            width: 70%;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .search-bar button {
            padding: 10px 20px;
            background-color: #ccad74;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .search-bar button:hover {
            background-color: #0056b3;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            text-align: left;
            padding: 10px;
        }

        th {
            background-color: #f2f2f2;
        }

        .no-results {
            text-align: center;
            margin-top: 20px;
            color: #999;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />
    <div class="container">
        <h1>Manager Feedback Page</h1>
        <div class="search-bar">
            <form action="ManagerFeedbackPageController" method="GET">
                <input type="text" name="searchQuery" placeholder="Search feedback by product ID, staff ID, or customer ID">
                <button type="submit">Search</button>
            </form>
        </div>

        <c:if test="${empty feedbackList}">
            <p class="no-results">No feedback available or no results found.</p>
        </c:if>

        <c:if test="${not empty feedbackList}">
            <table>
                <thead>
                    <tr>
                        <th>Type</th>
                        <th>Customer ID</th>
                        <th>Feedback On</th>
                        <th>Rating</th>
                        <th>Content</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="feedback" items="${feedbackList}">
                        <tr>
                            <td>on ${feedback.type}</td>
                            <td>${feedback.myCustomerID}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${feedback.type == 'driver'}">
                                        ${feedback.myStaffID}
                                    </c:when>
                                    <c:when test="${feedback.type == 'product'}">
                                        ${feedback.myProductID}
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                            </td>
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

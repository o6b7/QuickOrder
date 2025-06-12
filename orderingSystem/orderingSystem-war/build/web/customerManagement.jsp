<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
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
            margin-top: 30px;
        }
        .messages {
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 4px;
        }
        .messages div {
            margin-bottom: 10px;
        }
        .search-container {
            margin-bottom: 20px;
            display: flex;
        }
        .search-container input[type="text"] {
            flex: 7;
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px;
        }
        .search-container button {
            flex: 3;
            padding: 10px;
            font-size: 14px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 0 4px 4px 0;
        }
        .search-container button:hover {
            background-color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table th, table td {
            text-align: left;
            padding: 10px;
            border: 1px solid #ddd;
        }
        table th {
            background-color: #f2f2f2;
        }
        table td.actions {
            text-align: center;
        }
        table td.actions a {
            margin: 0 5px;
            text-decoration: none;
            color: #007bff;
        }
        table td.actions a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
    <!-- Include head and nav -->
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp">
        <jsp:param name="mainNav" value="CustomerManagement" />
    </jsp:include>

    <div class="container">
        <h1>Customer Management</h1>

        <!-- Success and Error Messages -->
        <div class="messages">
            <c:if test="${not empty successMessage}">
                <div style="color: green;">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div style="color: red;">${errorMessage}</div>
            </c:if>
        </div>

        <!-- Search Field -->
        <div class="search-container">
            <form action="CustomerManagementController" method="GET">
                <input type="text" name="search" placeholder="Search by name, email, or IC">
                <button type="submit">Search</button>
            </form>
        </div>

        <!-- Customer Table -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>IC</th>
                    <th>Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customerList}">
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.email}</td>
                        <td>${customer.phone}</td>
                        <td>${customer.gender}</td>
                        <td>${customer.IC}</td>
                        <td>${customer.address}</td>
                        <td class="actions">
                            <!-- Add mainNav parameter to edit and delete links -->
                            <a href="CustomerManagementController?action=edit&id=${customer.id}&
                               mainNav=CustomerManagement">
                                <img src="images/edit.png" alt="Edit Icon" width="20px">
                            </a>
                            <a href="CustomerDeleteController?id=${customer.id}&mainNav=CustomerManagement" 
                               onclick="return confirm('Are you sure?')">
                                <img src="images/trash.png" alt="Delete Icon" width="20px">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

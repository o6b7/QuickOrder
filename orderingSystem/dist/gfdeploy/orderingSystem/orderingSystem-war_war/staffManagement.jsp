<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Staff Management</title>
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
            flex: 7; /* Takes 70% of the space */
            padding: 10px;
            font-size: 14px;
            border: 1px solid #ddd;
            border-radius: 4px 0 0 4px; /* Rounded corners on the left */
        }
        .search-container button {
            flex: 3; /* Takes 30% of the space */
            padding: 10px;
            font-size: 14px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 0 4px 4px 0; /* Rounded corners on the right */
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
            vertical-align: middle; /* Ensures the content is vertically aligned */
        }

        table td.actions a {
            margin: 0 5px;
            text-decoration: none;
            color: #007bff;
            display: inline-block; /* Ensures no extra space below the images */
        }

        table td.actions a img {
            display: block; /* Removes inline-block spacing under the image */
        }

    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />

    <div class="container">
        <h1>Staff Management</h1>

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
            <form action="StaffManagementController" method="GET" style="width: 100%; display: flex;">
                <input type="text" name="search" placeholder="Search by ID, email, or name">
                <button type="submit">Search</button>
            </form>
        </div>

        <!-- Staff Table -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Gender</th>
                    <th>IC</th>
                    <th>Address</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="staff" items="${staffList}">
                    <tr>
                        <td>${staff.id}</td>
                        <td>${staff.email}</td>
                        <td>${staff.name}</td>
                        <td>${staff.phone}</td>
                        <td>${staff.gender}</td>
                        <td>${staff.IC}</td>
                        <td>${staff.address}</td>
                        <td>${staff.role}</td>
                        <td class="actions">
                            <a href="StaffManagementController?action=edit&id=${staff.id}">
                                <img src="images/edit.png" alt="Edit Icon" width="20px">
                            </a>
                            <a href="StaffManagementController?action=delete&id=${staff.id}" onclick="return confirm('Are you sure?')">
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

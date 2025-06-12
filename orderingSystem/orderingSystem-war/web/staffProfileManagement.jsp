<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Profile Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            width: 50%;
            margin: auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            margin-top: 50px;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-size: 14px;
            color: white;
        }
        .success {
            background-color: #4CAF50;
        }
        .error {
            background-color: #f44336;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
            color: #555;
            font-weight: bold;
        }
        input, textarea, button {
            padding: 12px;
            margin-top: 8px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        input:focus, textarea:focus {
            border-color: #e2d5bc;
            box-shadow: 0 0 6px rgba(226, 213, 188, 0.7);
            outline: none;
        }
        textarea {
            resize: none;
        }
        button {
            background-color: #e2d5bc;
            color: #6B4532;
            font-weight: bold;
            border: none;
            cursor: pointer;
            margin-top: 20px;
            transition-duration: 0.2s;
        }
        button:hover {
            background-color: #ccad74;
            color: white;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    
        <!-- Include the correct navigation bar based on userRole -->
    <c:choose>
        <c:when test="${sessionScope.userRole == 'Delivery Staff'}">
            <jsp:include page="driverNav.jsp" />
        </c:when>
        <c:when test="${sessionScope.userRole == 'Managing Staff'}">
            <jsp:include page="managerNav.jsp" />
        </c:when>
        <c:otherwise>
            <!-- Redirect to login or error page if userRole is invalid -->
            <c:redirect url="login.jsp" />
        </c:otherwise>
    </c:choose>
    
    <div class="container">
        <!-- Display success or error messages -->
        <c:if test="${not empty successMessage}">
            <div class="message success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="message error">${errorMessage}</div>
        </c:if>

        <h1>Edit Profile</h1>

        <!-- Main Form -->
        <form action="StaffProfileManagementController" method="POST">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" value="${staff.name}" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="${staff.email}" required>

            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" value="${staff.phone}" required>

            <label for="IC">Identification Card Number (IC)</label>
            <input type="text" id="IC" name="IC" value="${staff.IC}" required>

            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3" required>${staff.address}</textarea>

            <label for="currentPassword">Current Password</label>
            <input type="password" id="currentPassword" name="currentPassword" placeholder="Enter current password" required>

            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" placeholder="Leave empty to keep the current password" disabled>

            <div class="checkbox-container">
                <input type="checkbox" id="changePasswordCheckbox" onchange="document.getElementById('newPassword').disabled = !this.checked;">
                <label for="changePasswordCheckbox">I want to change the password</label>
            </div>

            <div class="submit-button">
                <button type="submit">Update Profile</button>
            </div>
        </form>
    </div>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Staff Profile</title>
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
        input, select, textarea, button {
            padding: 12px;
            margin-top: 8px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        input:focus, select:focus, textarea:focus {
            border-color: #e2d5bc;
            box-shadow: 0 0 6px rgba(226, 213, 188, 0.7);
            outline: none;
        }
        textarea {
            resize: none;
        }
        select {
            appearance: none; /* Remove default dropdown arrow */
            background: #fff url('data:image/svg+xml;charset=US-ASCII,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 4 5"><path fill="gray" d="M2 0L0 2h4z"></path></svg>') no-repeat right 12px center;
            background-size: 12px 12px;
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
        .checkbox-container {
            margin-top: 10px;
            display: flex;
            align-items: center;
        }
        .checkbox-container label {
            margin-left: 5px;
        }
        .submit-button {
            margin-top: 20px;
            text-align: center;
        }
        .role-container {
            display: flex;
            align-items: center;
            margin-top: 10px;
        }
        .role-container label {
            margin-left: 8px;
            color: #555;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />

    <div class="container">
        <!-- Display success or error messages -->
        <c:if test="${not empty successMessage}">
            <div class="message success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="message error">${errorMessage}</div>
        </c:if>

        <h1>Edit Staff</h1>

        <!-- Main Form -->
        <form action="EditStaffController" method="POST">
            <input type="hidden" name="id" value="${staff.id}">

            <label for="name">Name</label>
            <input type="text" id="name" name="name" value="${staff.name}" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="${staff.email}" required>

            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" value="${staff.phone}" required>

            <label for="gender">Gender</label>
            <select id="gender" name="gender" required>
                <option value="Male" <c:if test="${staff.gender == 'Male'}">selected</c:if>>Male</option>
                <option value="Female" <c:if test="${staff.gender == 'Female'}">selected</c:if>>Female</option>
            </select>

            <label for="IC">Identification Card Number (IC)</label>
            <input type="text" id="IC" name="IC" value="${staff.IC}" required>

            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3" required>${staff.address}</textarea>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Leave empty to keep the current password" disabled>

            <div class="checkbox-container">
                <input type="checkbox" id="changePasswordCheckbox" onchange="document.getElementById('password').disabled = !this.checked;">
                <label for="changePasswordCheckbox">I want to change the password</label>
            </div>

            <label>Role</label>
            <div class="role-container">
                <input type="radio" id="managingStaff" name="role" value="Managing Staff" <c:if test="${staff.role == 'Managing Staff'}">checked</c:if>>
                <label for="managingStaff">Managing Staff</label>
            </div>
            <div class="role-container">
                <input type="radio" id="deliveryStaff" name="role" value="Delivery Staff" <c:if test="${staff.role == 'Delivery Staff'}">checked</c:if>>
                <label for="deliveryStaff">Delivery Staff</label>
            </div>

            <div class="submit-button">
                <button type="submit">Update User</button>
            </div>
        </form>

    </div>
</body>
</html>

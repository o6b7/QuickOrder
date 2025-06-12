<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Staff</title>
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
            color: #555;
            font-weight: bold;
            display: block;
            margin-top: 20px; 
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
        button, button a {
            background-color: #e2d5bc;
            color: #6B4532;
            font-weight: bold;
            border: none;
            cursor: pointer;
            margin-top: 20px;
            transition-duration: 0.2s;
            width: 100%;
        }
        button:hover,button a{
            background-color: #ccad74;
            color: white;
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
        button a {
            text-decoration: none;
            color: white;
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

        <h1>Add Staff</h1>
        <form action="addStaffController" method="POST">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" placeholder="Enter full name" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter password" required>

            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" placeholder="Enter phone number" required>

            <label for="gender">Gender</label>
            <select id="gender" name="gender" required>
                <option value="" disabled selected>Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <label for="IC">Identification Card Number (IC)</label>
            <input type="text" id="IC" name="IC" placeholder="Enter IC" required>

            <label for="address">Address</label>
            <textarea id="address" name="address" placeholder="Enter address" rows="3" required></textarea>

            <label>Role</label>
            <div class="role-container">
                <input type="radio" id="managingStaff" name="role" value="Managing Staff" required>
                <label for="managingStaff">Managing Staff</label>
            </div>
            <div class="role-container">
                <input type="radio" id="deliveryStaff" name="role" value="Delivery Staff" required>
                <label for="deliveryStaff">Delivery Staff</label>
            </div>

            <button type="submit">Add Staff</button>
            
        </form>
    </div>
</body>
</html>

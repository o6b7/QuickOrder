<!DOCTYPE html>
<html>
<head>
    <title>Customer Registration</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; margin: 0; padding: 0; }
        .container { width: 50%; margin: auto; background: #fff; padding: 20px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); border-radius: 8px; margin-top: 50px; }
        h1 { text-align: center; color: #333; }
        .error-message { color: red; font-weight: bold; text-align: center; margin-bottom: 15px; }
        .success-message { color: green; font-weight: bold; text-align: center; margin-bottom: 15px; }
        form { display: flex; flex-direction: column; }
        label { margin-top: 10px; color: #555; }
        input, textarea, select, button { padding: 10px; margin-top: 5px; font-size: 14px; border: 1px solid #ddd; border-radius: 4px; }
        select { appearance: none; cursor: pointer; }
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
    <div class="container">
        <h1>Register New Customer</h1>

        <!-- Display Error Message -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <!-- Registration Form -->
        <form action="CustomerRegistrationController" method="POST">
            <label for="name">Name</label>
            <input type="text" id="name" name="name" placeholder="Enter your full name" required>

            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Enter your email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="Enter a secure password" required>

            <label for="gender">Gender</label>
            <select id="gender" name="gender" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select>

            <label for="IC">Identification Card Number (IC)</label>
            <input type="text" id="IC" name="IC" placeholder="Enter your IC number" required>

            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" placeholder="Enter your phone number">

            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3" placeholder="Enter your address"></textarea>

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>

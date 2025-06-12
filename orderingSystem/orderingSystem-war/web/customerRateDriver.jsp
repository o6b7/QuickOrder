<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Rate Driver</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 50%;
            margin: auto;
            margin-top: 50px;
            background: #fff;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
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
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        input:focus, textarea:focus {
            border-color: #ccad74;
            box-shadow: 0 0 6px rgba(204, 173, 116, 0.7);
            outline: none;
        }

        textarea {
            resize: none;
        }

        button {
            background-color: #ccad74;
            color: white;
            font-weight: bold;
            border: none;
            cursor: pointer;
            margin-top: 20px;
        }

        button:hover {
            background-color: #b39866;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="customerNav.jsp" />
    
    <div class="container">
        <h1>Rate Driver</h1>
        <form action="CustomerRateDriverController" method="POST">
            <input type="hidden" name="myStaffID" value="${myStaffID}">
            <input type="hidden" name="myProductID" value="${myProductID}">
            <input type="hidden" name="date" value="${date}">
            <input type="hidden" name="myCustomerID" value="${myCustomerID}">
            <label for="rating">Rating</label>
            <input type="number" id="rating" name="rating" min="1" max="5" required>
            <label for="content">Feedback</label>
            <textarea id="content" name="content" rows="5" placeholder="Write your feedback here..." required></textarea>
            <button type="submit">Submit Feedback</button>
        </form>

    </div>
</body>
</html>

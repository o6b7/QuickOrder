<!DOCTYPE html>
<html>
<head>
    <title>Managing Staff Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }
        .container {
            width: 80%;
            margin: auto;
            margin-top: 30px;
        }
        .dashboard-summary {
            background: #fff;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            padding: 20px;
            margin-top: 20px;
            display: flex;
            flex-wrap: wrap;
            text-align: center;
        }
        .dashboard-summary div {
            flex: 1 1 30%;
            margin: 10px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 8px;
        }
        .dashboard-summary div h3 {
            font-size: 18px;
            color: #333;
        }
        .dashboard-summary div p {
            font-size: 24px;
            font-weight: bold;
            color: black;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />

    <div class="container">
        <div class="dashboard-summary">
            <div>
                <h3>Total Products</h3>
                <p>${totalProducts}</p>
            </div>
            <div>
                <h3>Total Customers</h3>
                <p>${totalCustomers}</p>
            </div>
            <div>
                <h3>Female Customers</h3>
                <p>${femaleCustomers}</p>
            </div>
            <div>
                <h3>Male Customers</h3>
                <p>${maleCustomers}</p>
            </div>
            <div>
                <h3>Delivery Staff</h3>
                <p>${deliveryStaff}</p>
            </div>
            <div>
                <h3>Managing Staff</h3>
                <p>${managingStaff}</p>
            </div>
            <div>
                <h3>Total Orders</h3>
                <p>${totalOrders}</p>
            </div>
            <div>
                <h3>Feedbacks on Drivers</h3>
                <p>${driverFeedbacks}</p>
            </div>
            <div>
                <h3>Feedbacks on Products</h3>
                <p>${productFeedbacks}</p>
            </div>
        </div>
    </div>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>My Orders</title>
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

        .order-section {
            margin-bottom: 40px;
        }

        .order-section h2 {
            margin-bottom: 10px;
            color: #007bff;
        }

        .order-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .order-table th, .order-table td {
            text-align: left;
            padding: 10px;
            border: 1px solid #ddd;
        }

        .order-table th {
            background-color: #f2f2f2;
        }

        .product-container {
            display: flex;
            align-items: center;
        }

        .product-image {
            flex: 1;
            text-align: center;
            max-width: 150px;
        }

        .product-image img {
            max-width: 100%;
            max-height: 100px;
            object-fit: cover;
            border-radius: 5px;
        }

        .product-info {
            flex: 2;
            padding-left: 10px;
        }

        .pending {
            color: orange;
        }

        .out-for-delivery {
            color: blue;
        }

        .delivered {
            color: green;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="customerNav.jsp" />

    <div class="container">
        <h1>My Orders</h1>

        <!-- Display message after canceling an order -->
        <c:if test="${not empty param.message}">
            <p style="color: green; text-align: center;">${param.message}</p>
        </c:if>

        <!-- Search Form -->
        <form action="CustomerOrdersController" method="GET" style="margin-bottom: 20px;">
            <input type="text" name="searchOrderId" placeholder="Search by Order ID" style="padding: 10px; width: 80%;">
            <button type="submit" style="padding: 10px;">Search</button>
        </form>

        <!-- Pending Orders -->
        <div class="order-section">
            <h2>Pending Orders</h2>
            <c:if test="${empty pendingOrders}">
                <p>No pending orders available.</p>
            </c:if>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Action</th> <!-- Column for the cancel action -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${pendingOrders}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div class="product-info">
                                        <strong>${order.productName}</strong><br>
                                        ${order.productDescription}<br>
                                        <strong>Price:</strong> $${order.productPrice}
                                    </div>
                                </div>
                            </td>
                            <td class="pending">Pending</td>
                            <td>${order.date}</td>
                            <td>
                                <!-- Cancel Order Form -->
                                <form action="CustomerOrdersController" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="cancelOrder">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <button type="submit">Cancel Order</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Out for Delivery Orders -->
        <div class="order-section">
            <h2>Out for Delivery</h2>
            <c:if test="${empty outForDeliveryOrders}">
                <p>No orders out for delivery.</p>
            </c:if>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product</th>
                        <th>Status</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${outForDeliveryOrders}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div class="product-info">
                                        <strong>${order.productName}</strong><br>
                                        ${order.productDescription}<br>
                                        <strong>Price:</strong> $${order.productPrice}
                                    </div>
                                </div>
                            </td>
                            <td class="out-for-delivery">Out for Delivery</td>
                            <td>${order.date}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Delivered Orders -->
        <div class="order-section">
            <h2>Delivered</h2>
            <c:if test="${empty deliveredOrders}">
                <p>No delivered orders available.</p>
            </c:if>
            <table class="order-table">
                <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Product</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Feedback</th> <!-- New column for Feedback -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${deliveredOrders}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div class="product-info">
                                        <strong>${order.productName}</strong><br>
                                        ${order.productDescription}<br>
                                        <strong>Price:</strong> $${order.productPrice}
                                    </div>
                                </div>
                            </td>
                            <td class="delivered">Delivered</td>
                            <td>${order.date}</td>
                            <td>
                                <form action="CustomerProductsDetailsController" method="GET" style="display:inline;">
                                    <input type="hidden" name="id" value="${order.myProductID}">
                                    <button type="submit">Rate Product</button>
                                </form>
                                <form action="CustomerRateDriverController" method="GET" style="display:inline;">
                                    <input type="hidden" name="myStaffID" value="${order.myStaffID}">
                                    <input type="hidden" name="myProductID" value="${order.myProductID}">
                                    <input type="hidden" name="date" value="${order.date}">
                                    <button type="submit">Rate Driver</button>
                                </form>
                            </td>
                        </tr>
                        <!-- Feedback row -->
                        <c:if test="${order.feedback != null}">
                            <tr>
                                <td colspan="1">Feedback on Driver</td>
                                <td colspan="3">
                                    <p>Content: ${order.feedback.content}</p>
                                    <p>Rating: ${order.feedback.rating}</p>
                                </td>
                                <td>
                                    <form action="DeleteFeedbackController" method="POST">
                                        <input type="hidden" name="feedbackId" value="${order.feedback.feedbackId}">
                                        <button type="submit">Delete Feedback</button>
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Available Orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
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

        .message {
            text-align: center;
            margin-bottom: 20px;
            font-weight: bold;
            padding: 10px;
            border-radius: 5px;
        }

        .message.error {
            color: white;
            background-color: #dc3545;
        }

        .message.success {
            color: white;
            background-color: #28a745;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table th, table td {
            text-align: center;
            padding: 10px;
            border: 1px solid #ddd;
        }

        table th {
            background-color: #f2f2f2;
        }

        .actions button {
            margin: 0 5px;
            padding: 5px 10px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            background-color: #28a745;
            color: white;
        }

        .actions button:hover {
            background-color: #218838;
        }

        .payment-row {
            background-color: #d4edda;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="driverNav.jsp" />
    <div class="container">
        <h1>Available Orders</h1>

        <c:if test="${not empty message}">
            <p class="message ${messageType}">${message}</p>
        </c:if>

        <!-- Section for "Out for delivery" orders -->
        <h2>Out for Delivery</h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer Name</th>
                    <th>Customer Address</th>
                    <th>Product ID</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status == 'Out for delivery'}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>${order.myCustomerName}</td>
                            <td>${order.customerAddress}</td>
                            <td>${order.myProductID}</td>
                            <td>${order.productPrice}</td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                            <td>
                                <form action="DriverAvailableOrdersController" method="POST">
                                    <input type="hidden" name="action" value="deliveredAndPaid">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <button type="submit">Delivered & Payment Collected</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <!-- Section for "Delivered" orders -->
        <h2>Delivered Orders</h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer Name</th>
                    <th>Customer Address</th>
                    <th>Product ID</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status == 'Delivered'}">
                        <tr>
                            <td rowspan="2">${order.myOrderId}</td>
                            <td>${order.myCustomerName}</td>
                            <td>${order.customerAddress}</td>
                            <td>${order.myProductID}</td>
                            <td>${order.productPrice}</td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                        </tr>
                        <tr class="payment-row">
                            <td colspan="6">${order.productPrice} $ was collected in cash</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

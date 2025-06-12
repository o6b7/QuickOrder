<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order Management</title>
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
        
                .search-container {
            display: flex;
            margin-bottom: 20px;
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
            background-color: #ccad74;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 0 4px 4px 0;
            transition: background-color 0.3s ease;
        }

        .search-container button:hover {
            background-color: #b39866;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        h2 {
            text-align: left;
            margin-top: 30px;
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

        .product-container {
            text-align: center;
        }

        .product-image img {
            max-width: 100px;
            max-height: 100px;
            object-fit: cover;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .actions button, .driver-input {
            margin: 0 5px;
            padding: 5px 10px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }

        .approve {
            background-color: #28a745;
            color: white;
        }

        .reject {
            background-color: #dc3545;
            color: white;
        }

        .driver-input {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 5px;
            width: 80%;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />
    <div class="container">
        <h1>Order Management</h1>

        <c:if test="${not empty message}">
            <p class="message ${messageType}">${message}</p>
        </c:if>

        <div class="container">
            <form action="OrderManagementController" method="GET" class="search-container">
                <input type="hidden" name="mainNav" value="OrderManagement">
                <input type="text" name="searchOrderId" placeholder="Search by Order ID, Staff ID, or Customer ID" 
                       style="padding: 10px; width: 80%;">
                <button type="submit" style="padding: 10px;">Search</button>
            </form>
        </div>

        <!-- Not Assigned Orders -->
        <h2>Not Assigned</h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer ID</th>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Driver ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status == 'Not Assigned'}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>${order.myCustomerID}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div><strong>${order.productName}</strong></div>
                                </div>
                            </td>
                            <td>${order.productPrice}</td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                            <td>
                                <!-- Updated Driver ID input with suggestions -->
                                <form action="OrderManagementController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <input list="driverSuggestions" name="driverId" 
                                           value="<c:out value='${order.myStaffID == "D0000" ? "" : order.myStaffID}'/>"
                                           placeholder="Enter Driver ID" class="driver-input" required>
                                    <datalist id="driverSuggestions">
                                        <c:forEach var="driver" items="${driverSuggestions}">
                                            <option value="${driver.id}">${driver.id} - ${driver.orderCount} orders</option>
                                        </c:forEach>
                                    </datalist>
                                    <button class="approve">Approve</button>
                                </form>
                            </td>
                            <td class="actions">
                                <form action="OrderManagementController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <button class="reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <!-- Out for Delivery Orders -->
        <h2>Out for Delivery</h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer ID</th>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Driver ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status == 'Out for delivery'}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>${order.myCustomerID}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div><strong>${order.productName}</strong></div>
                                </div>
                            </td>
                            <td>${order.productPrice}</td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                            <td>
                                <!-- Updated Driver ID input with suggestions -->
                                <form action="OrderManagementController" method="POST" target="hiddenFrame" style="display: inline;">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <input list="driverSuggestions" name="driverId" 
                                           value="<c:out value='${order.myStaffID}'/>"
                                           placeholder="Enter Driver ID" class="driver-input" required>
                                    <datalist id="driverSuggestions">
                                        <c:forEach var="driver" items="${driverSuggestions}">
                                            <option value="${driver.id}">${driver.id} - ${driver.orderCount} orders</option>
                                        </c:forEach>
                                    </datalist>
                                    <button class="approve">Reassign</button>
                                </form>
                            </td>
                            <td class="actions">
                                <form action="OrderManagementController" method="POST" target="hiddenFrame" style="display: inline;">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <button class="reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <h2>Rejected</h2>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer ID</th>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Driver ID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status == 'Rejected'}">
                        <tr>
                            <td>${order.myOrderId}</td>
                            <td>${order.myCustomerID}</td>
                            <td>
                                <div class="product-container">
                                    <div class="product-image">
                                        <img src="data:image/png;base64,${order.productImage}" alt="${order.productName}">
                                    </div>
                                    <div><strong>${order.productName}</strong></div>
                                </div>
                            </td>
                            <td>${order.productPrice}</td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                            <td>
                                <!-- Updated Driver ID input with suggestions -->
                                <form action="OrderManagementController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <input list="driverSuggestions" name="driverId" 
                                           placeholder="Enter Driver ID" class="driver-input" required>
                                    <datalist id="driverSuggestions">
                                        <c:forEach var="driver" items="${driverSuggestions}">
                                            <option value="${driver.id}">${driver.id} - ${driver.orderCount} orders</option>
                                        </c:forEach>
                                    </datalist>
                                    <button class="approve">Approve</button>
                                </form>
                            </td>
                            <td class="actions">
                                <form action="OrderManagementController" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="orderId" value="${order.myOrderId}">
                                    <button class="reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

    </div>
</body>
</html>

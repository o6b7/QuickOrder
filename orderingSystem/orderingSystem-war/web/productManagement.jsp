<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Management</title>
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
        .search-container {
            margin-bottom: 20px;
            display: flex;
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
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 0 4px 4px 0;
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
        .product-image {
            text-align: center;
            vertical-align: middle;
        }
        .product-image img {
            max-width: 200px;
            max-height: 200px;
            object-fit: cover;
        }
        .product-info {
            padding: 10px;
            vertical-align: top;
        }
        .description-field {
            text-align: left;
            padding: 10px;
            font-style: italic;
            background-color: #f9f9f9;
        }
        .actions {
            text-align: center;
            vertical-align: middle;
        }
        .actions img {
            width: 20px;
            margin: 0 5px;
        }
    </style>
</head>
<body>
    <!-- Include head.jsp -->
    <jsp:include page="head.jsp" />
    <!-- Include the managerNav.jsp and set the mainNav parameter -->
    <jsp:include page="managerNav.jsp">
        <jsp:param name="mainNav" value="ProductManagement" />
    </jsp:include>

    <div class="container">
        <h1>Product Management</h1>
        <div class="search-container">
            <form action="ProductManagementController" method="GET">
                <input type="text" name="search" placeholder="Search by Name or Category">
                <button type="submit">Search</button>
            </form>
        </div>
        <table>
            <thead>
                <tr>
                    <th>Picture</th>
                    <th colspan="3">Product Info</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productList}">
                    <tr>
                        <td rowspan="3" class="product-image">
                            <img src="data:image/png;base64,${product.base64Image}" alt="${product.name}">
                        </td>
                        <td><strong>ID:</strong> ${product.id}</td>
                        <td><strong>Name:</strong> ${product.name}</td>
                        <td><strong>Category:</strong> ${product.category}</td>
                        <td rowspan="3" class="actions">
                            <!-- Add mainNav parameter to the edit and delete links -->
                            <a href="ProductManagementController?action=edit&id=${product.id}&mainNav=ProductManagement">
                                <img src="images/edit.png" alt="Edit">
                            </a>
                            <a href="ProductDeleteController?id=${product.id}&mainNav=ProductManagement" 
                               onclick="return confirm('Are you sure you want to delete this product?');">
                               <img src="images/trash.png" alt="Delete">
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td><strong>Price:</strong> ${product.price}</td>
                        <td><strong>Stock:</strong> ${product.stock}</td>
                        <td><strong>Publish Date:</strong> ${product.publishDate}</td>
                    </tr>
                    <tr>
                        <td colspan="3" class="description-field">
                            <strong>Description:</strong> ${product.description}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

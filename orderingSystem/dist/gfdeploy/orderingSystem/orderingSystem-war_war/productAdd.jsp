<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
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
            border-radius: 8px;
            margin-top: 50px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .error-message {
            color: red;
            font-weight: bold;
            text-align: center;
            margin-bottom: 15px;
        }
        label {
            color: #555;
            font-weight: bold;
            display: block;
            margin-top: 20px; 
        }
        input, textarea, select, button {
            margin-top: 8px;
            padding: 12px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 100%;
            box-sizing: border-box;
        }
        button {
            background-color: #e2d5bc;
            color: #6B4532;
            font-weight: bold;
            border: none;
            cursor: pointer;
            margin-top: 20px;
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
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />

    <div class="container">
        <h1>Add Product</h1>
        
        <!-- Display error message if any -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>

        <form action="AddProductController" method="POST" enctype="multipart/form-data">
            <label for="name">Product Name</label>
            <input type="text" id="name" name="name" placeholder="Enter product name" required>

            <label for="description">Description <span style="color: red;">(only 255 characters will be stored)</span></label>
            <textarea id="description" name="description" rows="3" placeholder="Enter product description"></textarea>

            <label for="price">Price</label>
            <input type="number" id="price" name="price" placeholder="Enter product price" step="0.01" required>

            <label for="stock">Stock</label>
            <input type="number" id="stock" name="stock" placeholder="Enter stock quantity" required>

            <label for="category">Category</label>
            <input type="text" id="category" name="newCategory" placeholder="Enter new category">

            <label for="picture">Picture</label>
            <input type="file" id="picture" name="picture" accept="image/*" required>

            <button type="submit">Add Product</button>
        </form>
    </div>
</body>
</html>

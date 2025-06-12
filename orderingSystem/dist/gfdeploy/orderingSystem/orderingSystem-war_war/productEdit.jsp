<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Product</title>
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
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
            font-weight: bold;
            color: #555;
        }
        input, textarea, button {
            margin-top: 8px;
            padding: 12px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        input:focus, textarea:focus {
            border-color: #007bff;
            box-shadow: 0 0 6px rgba(0, 123, 255, 0.5);
            outline: none;
        }
        textarea {
            resize: none;
        }
        button {
            background-color: #007bff;
            color: white;
            font-weight: bold;
            border: none;
            cursor: pointer;
            margin-top: 20px;
            transition: background-color 0.2s, color 0.2s;
        }
        button:hover {
            background-color: #0056b3;
        }
        .checkbox-container {
            margin-top: 15px;
            display: flex;
            align-items: center;
        }
        .checkbox-container label {
            margin-left: 8px;
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="managerNav.jsp" />
    <div class="container">
        <h1>Edit Product</h1>
        <form action="ProductEditController" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${product.id}">
            
            <label for="name">Name</label>
            <input type="text" id="name" name="name" value="${product.name}" required>
            
            <label for="description">Description</label>
            <textarea id="description" name="description" rows="4" required>${product.description}</textarea>
            
            <label for="price">Price</label>
            <input type="number" id="price" name="price" value="${product.price}" step="0.01" required>
            
            <label for="stock">Stock</label>
            <input type="number" id="stock" name="stock" value="${product.stock}" required>
            
            <label for="category">Category</label>
            <input type="text" id="category" name="category" value="${product.category}" readonly>

            <label for="picture">Picture</label>
            <input type="file" id="picture" name="picture" accept="image/*">
            
            <div class="submit-button">
                <button type="submit">Update Product</button>
            </div>
        </form>
    </div>
</body>
</html>

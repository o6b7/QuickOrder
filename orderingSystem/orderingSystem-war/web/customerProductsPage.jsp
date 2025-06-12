<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Customer Products</title>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f9f9f9;
            color: #333333;
            line-height: 1.6;
            margin: 0;
        }

        .container {
            width: 90%;
            margin: 0 auto;
            margin-top: 2rem;
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

        .cards-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .product-card {
            background-color: #EEE;
            border-radius: 20px;
            overflow: hidden;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            height: 100%;
        }

        .product-card__image img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .product-card__info {
            padding: 15px;
            display: flex;
            flex-direction: column;
        }

        .product-card__price-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: auto;
        }

        .product-card__price {
            font-size: 1.2rem;
            font-weight: 600;
            color: #ccad74;
        }

        .product-card__btn {
            background-color: #ccad74;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 50px;
            font-size: 0.9rem;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .product-card__btn:hover {
            background-color: #b39866;
        }

        iframe {
            display: none; /* Hide iframe */
        }
    </style>
</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="customerNav.jsp" />

    <h1 style="text-align: center; margin-bottom: 20px;">Customer Products</h1>

    <!-- Search Field -->
    <div class="container">
        <form action="CustomerProductsPageController" method="GET" class="search-container">
            <input type="text" name="search" placeholder="Search by product name or category" value="${param.search}">
            <button type="submit">Search</button>
        </form>
    </div>

    <!-- Product Cards Grid -->
    <div class="container">
        <div class="cards-grid">
            <c:forEach var="product" items="${productList}">
                <div class="product-card">
                    <div class="product-card__image">
                        <a href="CustomerProductsDetailsController?id=${product.id}">
                            <img src="data:image/png;base64,${product.base64Image}" alt="${product.name}">
                        </a>
                    </div>
                    <div class="product-card__info">
                        <a href="CustomerProductsDetailsController?id=${product.id}" style="text-decoration: none; color: inherit;">
                            <h2 class="product-card__title">${product.name}</h2>
                        </a>
                        <p class="product-card__description">${product.description}</p>
                        <div class="product-card__price-row">
                            <span class="product-card__price">$${product.price}</span>
                            <form target="hiddenFrame" action="AddToCartController" method="POST">
                                <input type="hidden" name="productId" value="${product.id}">
                                <button type="submit" class="product-card__btn">
                                    <span>&#128722;</span> Add to Cart
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <!-- Hidden iframe to handle form submission without page reload -->
    <iframe name="hiddenFrame"></iframe>
</body>
</html>

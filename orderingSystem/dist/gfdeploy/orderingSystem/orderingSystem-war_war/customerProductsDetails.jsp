<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f9f9f9;
            color: #333333;
            line-height: 1.6;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 70%;
            margin: 2rem auto;
            background-color: #ffffff;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .section {
            margin-bottom: 30px;
        }

        .section-title {
            font-size: 1.5rem;
            font-weight: bold;
            color: #333333;
            margin-bottom: 10px;
            border-bottom: 2px solid #ccad74;
            padding-bottom: 5px;
        }

        .product-image img {
            max-width: 100%;
            border-radius: 10px;
            object-fit: cover;
        }

        .product-info-row {
            margin-bottom: 10px;
        }

        .product-info-row strong {
            display: inline-block;
            width: 150px;
            font-weight: bold;
        }

        .price {
            font-size: 1.5rem;
            font-weight: bold;
            color: #ccad74;
            margin-top: 15px;
        }

        .button-row {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;
            font-size: 1rem;
            background-color: #ccad74;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #b39866;
        }
        
        .feedback-section {
            margin-top: 20px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 10px;
        }

        .feedback-section h2 {
            margin-bottom: 15px;
            color: #ccad74;
        }

        .feedback-section form textarea {
            width: 100%;
            height: 80px;
            margin-bottom: 10px;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        .feedback-section form select {
            margin-bottom: 10px;
            padding: 5px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        .feedback-section form button {
            padding: 10px 20px;
            background-color: #ccad74;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .feedback-section form button:hover {
            background-color: #b39866;
        }

        .feedback-list .feedback-item {
            margin-top: 15px;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        // Handle feedback form submission
        $('form[action="CustomerFeedbackController"]').on('submit', function (e) {
            e.preventDefault(); // Prevent the default form submission

            const formData = $(this).serialize(); // Serialize the form data

            $.ajax({
                url: 'CustomerFeedbackController',
                type: 'POST',
                data: formData,
                success: function (response) {
                    // Update the feedback list with the response HTML
                    $('.feedback-list').html(response);
                },
                error: function () {
                    alert('Error submitting feedback.');
                }
            });
        });
    });
</script>

</head>
<body>
    <jsp:include page="head.jsp" />
    <jsp:include page="customerNav.jsp" />

    <div class="container">
        <!-- Product Image Section -->
        <div class="section">
            <div class="section-title">Product Image</div>
            <div class="product-image">
                <img src="data:image/png;base64,${product.base64Image}" alt="${product.name}" width="200px">
            </div>
        </div>

        <!-- Product Details Section -->
        <div class="section">
            <div class="section-title">Product Details</div>
            <div class="product-info-row"><strong>Product Name:</strong> ${product.name}</div>
            <div class="product-info-row"><strong>Description:</strong> ${product.description}</div>
            <div class="product-info-row"><strong>Category:</strong> ${product.category}</div>
            <div class="product-info-row"><strong>Publish Date:</strong> ${product.publishDate}</div>
        </div>

        <!-- Pricing and Stock Section -->
        <div class="section">
            <div class="section-title">Price and Stock</div>
            <div class="product-info-row"><strong>Price:</strong> <span class="price">$${product.price}</span></div>
            <div class="product-info-row"><strong>Stock:</strong> ${product.stock}</div>
        </div>

        <!-- Button Row -->
        <div class="button-row">
        <!-- Add to Cart Form -->
        <form id="addToCartForm" method="POST">
            <input type="hidden" name="productId" value="${product.id}">
            <button type="submit" class="button">Add to Cart</button>
        </form>


            <!-- Back to Products Button -->
            <a href="CustomerProductsPageController" class="button">Back to Products</a>

        </div>
            
        <div class="section">
        <div class="section-title">Feedback</div>

        <!-- Feedback Section -->
        <div class="feedback-section">
            <h2>Feedback</h2>

            <!-- Feedback Form -->
            <form action="CustomerFeedbackController" method="POST">
                <input type="hidden" name="productId" value="${product.id}">
                <textarea name="content" placeholder="Write your feedback here..." required></textarea>
                <select name="rating" required>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
                <button type="submit">Submit Feedback</button>
            </form>

            <!-- Feedback List -->
            <div class="feedback-list">
                <c:forEach var="feedback" items="${feedbackList}">
                    <div class="feedback-item" style="border: 1px solid #ddd; border-radius: 10px; padding: 20px; margin-bottom: 15px;">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <strong>${feedback.customerName}</strong>
                            <span>${feedback.date}</span>
                        </div>
                        <p style="margin-top: 15px; white-space: pre-wrap;">${feedback.content}</p>
                        <p><strong>Rating:</strong> ${feedback.rating} / 5</p>
                    </div>
                </c:forEach>
            </div>

        </div>
    </div>
</body>
</html>

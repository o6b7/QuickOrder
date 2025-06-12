<%@ page session="true" %>
<div class="header">
    <div class="system-name">
        <img src="images/logo.png" alt="logo" width="120px">
    </div>
    <div class="profile-icon">
        <!-- Conditional display for Logout Icon -->
        <a href="LoginController?action=logout" 
           class="logout-icon" 
           style="${not empty sessionScope.userId ? 'display: block;' : 'display: none;'}">
            <img src="images/logout.png" alt="Logout Icon" width="30px">
        </a>

        <!-- Conditional display for Login and Register buttons -->
        <div class="auth-buttons" style="${not empty sessionScope.userId ?
                                           'display: none;' : 'display: block;'}">
            <a href="login.jsp" class="btn">Login</a>
            <a href="customerRegistration.jsp" class="btn">Register</a>
        </div>
    </div>
</div>

<style>
    /* Header Styles */
    .header {
        background-color: #ccad74;
        color: white;
        padding: 5px 40px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .header .system-name {
        font-size: 24px;
        font-weight: bold;
    }

    .header .profile-icon {
        display: flex;
        align-items: center;
        gap: 10px;
    }

    .header .profile-icon img {
        cursor: pointer;
    }

    /* Button Styles */
    .btn {
        background-color: white;
        color: #6B4532;
        border: none;
        padding: 10px 20px;
        text-decoration: none;
        font-size: 16px;
        font-weight: bold;
        border-radius: 5px;
        transition: background-color 0.3s ease, color 0.3s ease;
    }

    .btn:hover {
        background-color: #6B4532;
        color: white;
    }

    /* Utility classes for visibility (Optional for more readability) */
    .logout-icon {
        text-align: center;
    }

    .auth-buttons {
        display: flex;
        gap: 10px;
    }
</style>

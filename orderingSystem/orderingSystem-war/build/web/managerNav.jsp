<div class="nav">
    <a href="ManagingReportsController?mainNav=Reports" 
       class="${param.mainNav == 'Reports' ? 'active' : ''}">Reports</a>

    <a href="CustomerManagementController?mainNav=CustomerManagement" 
       class="${param.mainNav == 'CustomerManagement' ? 'active' : ''}">Customer Management</a>
       
    <div class="dropdown ${param.mainNav == 'StaffManagement' ? 'active' : ''}">
        <a href="#">Staff Management</a>
        <div class="dropdown-content">
            <a href="addStaff.jsp?mainNav=StaffManagement">Add New Staff</a>
            <a href="StaffManagementController?mainNav=StaffManagement">Manage Staff</a>
        </div>
    </div>
    
    <div class="dropdown ${param.mainNav == 'ProductManagement' ? 'active' : ''}">
        <a href="#">Products Management</a>
        <div class="dropdown-content">
            <a href="productAdd.jsp?mainNav=ProductManagement">Add New Products</a>
            <a href="ProductManagementController?mainNav=ProductManagement">Manage Products</a>
        </div>
    </div>
    
    <a href="OrderManagementController?mainNav=OrderManagement" 
       class="${param.mainNav == 'OrderManagement' ? 'active' : ''}">Order Management</a>

    <a href="StaffProfileManagementController?mainNav=ProfileManagement" 
       class="${param.mainNav == 'ProfileManagement' ? 'active' : ''}">Profile Management</a>

    <a href="ManagerFeedbackPageController?mainNav=Feedbacks" 
       class="${param.mainNav == 'Feedbacks' ? 'active' : ''}">Feedbacks</a>
</div>

<style>
    .nav {
        background-color: #e2d5bc;
        display: flex;
        justify-content: space-between;
    }
    .nav a {
        flex: 1;
        text-align: center;
        color: #6B4532;
        text-decoration: none;
        font-size: 16px;
        font-weight: bold;
        padding: 12px 0;
        box-sizing: border-box;
        transition: background-color 0.2s, color 0.2s;
    }
    .nav a:hover, .nav .active > a {
        background-color: #ccad74;
        color: white;
    }
    .dropdown {
        position: relative;
        flex: 1;
        text-align: center;
    }
    .dropdown a {
        display: block;
        padding: 12px 0;
        color: #6B4532;
        text-decoration: none;
        font-size: 16px;
        font-weight: bold;
    }
    .dropdown-content {
        display: none;
        position: absolute;
        top: 100%;
        left: 0;
        background-color: #e2d5bc;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        z-index: 1000;
        border-radius: 4px;
        width: 100%;
    }
    .dropdown-content a {
        padding: 10px;
        text-align: center;
        color: #6B4532;
        font-weight: normal;
    }
    .dropdown-content a:hover {
        background-color: #ccad74;
        color: white;
    }
    .dropdown:hover .dropdown-content {
        display: block;
    }
</style>

package dto;

import java.time.LocalDate;
import model.MyOrder;
import model.MyProduct;

import java.util.Map;

public class OrderDTO {
    private String myOrderId;
    private String myCustomerID; // Field for customer ID
    private String myCustomerName; // Field for customer name
    private String customerAddress;
    private String myProductID;  // Field for product ID
    private String myStaffID;    // Field for staff/driver ID
    private String productName;
    private String productDescription;
    private String productImage;
    private double productPrice;
    private LocalDate date;
    private String status;
    private String payment;
    private String driverName;    // Field for driver name
    private String driverContact; // Field for driver contact information
    private Map<String, Object> feedback; // New field for feedback information
    

    public OrderDTO() {}

    public OrderDTO(MyOrder order, MyProduct product) {
        this.myOrderId = order.getMyOrderId();
        this.myCustomerID = order.getMyCustomerID(); // Initialize customer ID
        this.myProductID = order.getMyProductID();   // Initialize product ID
        this.myStaffID = order.getMyStaffID();       // Initialize staff ID
        this.productName = product != null ? product.getName() : "Unknown Product";
        this.productDescription = product != null ? product.getDescription() : "No Description";
        this.productImage = product != null ? product.getBase64Image() : null;
        this.productPrice = product != null ? product.getPrice() : 0.0;
        this.date = order.getDate();
        this.status = order.getStatus();
        this.payment = order.getPayment();
        this.driverName = "N/A";    // Default value, can be updated later
        this.driverContact = "N/A"; // Default value, can be updated later
    }

    public String getMyOrderId() {
        return myOrderId;
    }

    public void setMyOrderId(String myOrderId) {
        this.myOrderId = myOrderId;
    }

    public String getMyCustomerID() {
        return myCustomerID;
    }

    public void setMyCustomerID(String myCustomerID) {
        this.myCustomerID = myCustomerID;
    }

    public String getMyCustomerName() {
        return myCustomerName;
    }

    public void setMyCustomerName(String myCustomerName) {
        this.myCustomerName = myCustomerName;
    }

    public String getMyProductID() {
        return myProductID;
    }

    public void setMyProductID(String myProductID) {
        this.myProductID = myProductID;
    }

    public String getMyStaffID() {
        return myStaffID;
    }

    public void setMyStaffID(String myStaffID) {
        this.myStaffID = myStaffID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public String getDriverInfo() {
        if ("D0000".equals(myStaffID)) {
            return "No driver yet";
        } else {
            return driverName + " (" + driverContact + ")";
        }
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Map<String, Object> getFeedback() {
        return feedback;
    }

    public void setFeedback(Map<String, Object> feedback) {
        this.feedback = feedback;
    }
}

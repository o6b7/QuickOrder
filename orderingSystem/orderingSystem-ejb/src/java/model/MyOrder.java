package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "MyOrder") // Ensure this matches the database table name
public class MyOrder implements Serializable {

    @Id
    @Column(name = "MYORDERID", nullable = false, length = 10)
    private String myOrderId;

    @Column(name = "MYCUSTOMERID", nullable = false, length = 10)
    private String myCustomerID;

    @Column(name = "MYSTAFFID", nullable = false, length = 10)
    private String myStaffID;

    @Column(name = "MYPRODUCTID", nullable = false, length = 10)
    private String myProductID;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "PAYMENT", nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'Not Paid'")
    private String payment = "Not Paid"; // Default value for new orders

    public MyOrder() {
    }

    public MyOrder(String myOrderId, String myCustomerID, String myStaffID, String myProductID, LocalDate date, String status, String payment) {
        this.myOrderId = myOrderId;
        this.myCustomerID = myCustomerID;
        this.myStaffID = myStaffID;
        this.myProductID = myProductID;
        this.date = date;
        this.status = status;
        this.payment = payment;
    }

    // Getters and Setters
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

    public String getMyStaffID() {
        return myStaffID;
    }

    public void setMyStaffID(String myStaffID) {
        this.myStaffID = myStaffID;
    }

    public String getMyProductID() {
        return myProductID;
    }

    public void setMyProductID(String myProductID) {
        this.myProductID = myProductID;
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
}

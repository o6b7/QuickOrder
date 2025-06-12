package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class MyFeedback implements Serializable {

    @Id
    @Column(name = "MYFEEDBACKID", nullable = false, length = 10)
    private String myFeedbackID;

    @Column(name = "MYPRODUCTID", nullable = false, length = 10)
    private String myProductID;

    @Column(name = "MYCUSTOMERID", nullable = false, length = 10)
    private String myCustomerID;

    @Column(name = "MYSTAFFID", length = 10)
    private String myStaffID;

    @Column(name = "CONTENT", nullable = false, length = 500)
    private String content;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;
    
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type; // 'product' or 'driver'


    public String getMyFeedbackID() {
        return myFeedbackID;
    }

    public void setMyFeedbackID(String myFeedbackID) {
        this.myFeedbackID = myFeedbackID;
    }

    public String getMyProductID() {
        return myProductID;
    }

    public void setMyProductID(String myProductID) {
        this.myProductID = myProductID;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
        public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

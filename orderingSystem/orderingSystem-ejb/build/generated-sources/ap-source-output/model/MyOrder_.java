package model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-12-27T16:31:30")
@StaticMetamodel(MyOrder.class)
public class MyOrder_ { 

    public static volatile SingularAttribute<MyOrder, String> myStaffID;
    public static volatile SingularAttribute<MyOrder, LocalDate> date;
    public static volatile SingularAttribute<MyOrder, String> myProductID;
    public static volatile SingularAttribute<MyOrder, String> myOrderId;
    public static volatile SingularAttribute<MyOrder, String> payment;
    public static volatile SingularAttribute<MyOrder, String> myCustomerID;
    public static volatile SingularAttribute<MyOrder, String> status;

}
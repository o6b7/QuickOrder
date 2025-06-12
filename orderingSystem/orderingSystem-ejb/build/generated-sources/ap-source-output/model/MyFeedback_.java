package model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-12-27T16:31:30")
@StaticMetamodel(MyFeedback.class)
public class MyFeedback_ { 

    public static volatile SingularAttribute<MyFeedback, String> myStaffID;
    public static volatile SingularAttribute<MyFeedback, LocalDate> date;
    public static volatile SingularAttribute<MyFeedback, Integer> rating;
    public static volatile SingularAttribute<MyFeedback, String> myProductID;
    public static volatile SingularAttribute<MyFeedback, String> myFeedbackID;
    public static volatile SingularAttribute<MyFeedback, String> myCustomerID;
    public static volatile SingularAttribute<MyFeedback, String> type;
    public static volatile SingularAttribute<MyFeedback, String> content;

}
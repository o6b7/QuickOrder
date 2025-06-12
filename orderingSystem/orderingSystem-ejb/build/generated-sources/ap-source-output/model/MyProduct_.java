package model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2024-12-27T16:31:30")
@StaticMetamodel(MyProduct.class)
public class MyProduct_ { 

    public static volatile SingularAttribute<MyProduct, Double> price;
    public static volatile SingularAttribute<MyProduct, String> name;
    public static volatile SingularAttribute<MyProduct, LocalDate> publishDate;
    public static volatile SingularAttribute<MyProduct, String> description;
    public static volatile SingularAttribute<MyProduct, String> id;
    public static volatile SingularAttribute<MyProduct, Integer> stock;
    public static volatile SingularAttribute<MyProduct, String> category;
    public static volatile SingularAttribute<MyProduct, byte[]> picture;

}
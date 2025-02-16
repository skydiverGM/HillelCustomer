package imaks.hillelcustomer.entity;

import lombok.Data;

@Data
public class Customer {

    private Long id;
    private String fullName;
    private String email;
    private String socialSecurityNumber;
}

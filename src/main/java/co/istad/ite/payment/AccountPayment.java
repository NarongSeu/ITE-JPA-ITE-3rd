package co.istad.ite.payment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accountpayments")
public class AccountPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,  unique = true, length = 255)
    private String accountUserName;

    @Column(nullable = false,  unique = true, length = 255)
    private String accountUserEmail;

    @Column(nullable = false,  unique = true, length = 255)
    private String accountUserPassword;

}




package co.istad.ite.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_lines")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne
//    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
//    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private BigDecimal unitPrice;
}

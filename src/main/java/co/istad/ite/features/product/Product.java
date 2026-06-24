package co.istad.ite.features.product;


import co.istad.ite.features.category.Category;
import co.istad.ite.features.order.OrderLine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 100, nullable = false,  unique = true)
    private String code;

    @Column(length = 500)
    private String description;

    @Column(nullable = false,  unique = true)
    private String slug;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private Boolean isAvailable;

    @Column(nullable = false)
    private Boolean isDelete;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;
}

package co.istad.ite.features.category;


import co.istad.ite.features.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity // class replace to JPA
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Implement
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)// custome colunm name
    private String name;

    private String description;
    private String icon;
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.REMOVE)
    private List<Category> childCategories;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}

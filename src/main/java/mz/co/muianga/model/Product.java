package mz.co.muianga.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString (callSuper = true)
@Entity
@Table (name = "products")
public class Product extends AbstractEntity {

    @NotNull
    @Column (name = "name", nullable = false)
    private String name;

    @NotNull
    @Column (name = "description", nullable = false)
    private String description;

    @NotNull
    @Column (name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated (EnumType.STRING)
    @Column (name = "status", nullable = false)
    private ProductStatus status;

    @Column (name = "sales_counter")
    private Integer salesCounter;

    @OneToMany (fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable (name = "products_reviews",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn (name = "reviews_id"))
    private Set<Review> reviews = new HashSet<>();

    @ManyToOne
    @JoinColumn (name = "category_id")
    private Category category;

    public Product(String name, String description, BigDecimal price,
                   ProductStatus status, Integer salesCounter,
                   Set<Review> reviews, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.salesCounter = salesCounter;
        this.reviews = reviews;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(status, product.status) && Objects.equals(salesCounter, product.salesCounter) && Objects.equals(reviews, product.reviews) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, status, salesCounter, reviews, category);
    }
}

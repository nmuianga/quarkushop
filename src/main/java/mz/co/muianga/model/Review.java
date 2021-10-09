package mz.co.muianga.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString (callSuper = true)
@Entity
@Table (name = "reviews")
public class Review extends AbstractEntity {

    @NotNull
    @Column (name = "title", nullable = false)
    private String title;

    @NotNull
    @Column (name = "description", nullable = false)
    private String descriprion;

    @NotNull
    @Column (name = "rating", nullable = false)
    private Long rating;

    public Review(@NotNull String title, String descriprion, Long rating) {
        this.title = title;
        this.descriprion = descriprion;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(title, review.title) &&
                Objects.equals(descriprion, review.descriprion) &&
                Objects.equals(rating, review.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, descriprion, rating);
    }
}

package mz.co.muianga.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Address {

    @Column (name = "address_1")
    private String addres1;

    @Column (name = "address_2")
    private String addres2;

    @Column (name = "city")
    private String city;

    @NotNull
    @Size (max = 10)
    @Column (name = "postcode", length = 10, nullable = false)
    private String postcode;

    @NotNull
    @Size (max = 2)
    @Column (name = "country", length = 2, nullable = false)
    private String country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addres1, address.addres1) &&
                Objects.equals(addres2, address.addres2) &&
                Objects.equals(city, address.city) &&
                Objects.equals(postcode, address.postcode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addres1, addres2, city, postcode, country);
    }
}

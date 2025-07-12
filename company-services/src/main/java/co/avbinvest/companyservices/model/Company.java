package co.avbinvest.companyservices.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String companyName;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 7, fraction = 2)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal budget;
}

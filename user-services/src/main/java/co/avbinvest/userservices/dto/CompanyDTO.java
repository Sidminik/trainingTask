package co.avbinvest.userservices.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal budget;
}

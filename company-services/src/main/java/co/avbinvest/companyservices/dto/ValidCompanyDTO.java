package co.avbinvest.companyservices.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidCompanyDTO {

    @NotNull
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal budget;
}

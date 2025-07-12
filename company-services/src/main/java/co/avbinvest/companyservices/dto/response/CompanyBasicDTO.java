package co.avbinvest.companyservices.dto.response;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CompanyBasicDTO(
    @Min(1) Long id,
    @NotBlank @Size(min = 2, max = 100) String companyName,
    @NotNull @DecimalMin(value = "0.01") BigDecimal budget
){}

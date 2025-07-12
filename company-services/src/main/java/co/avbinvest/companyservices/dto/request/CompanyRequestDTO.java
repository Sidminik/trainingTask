package co.avbinvest.companyservices.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CompanyRequestDTO(
    @NotBlank @Size(min = 2, max = 100) String companyName,
    @NotNull @DecimalMin(value = "0.01") BigDecimal budget
){}

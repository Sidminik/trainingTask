package co.avbinvest.userservices.dto.response;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CompanyDTO (
    @Min(1) Long id,
    @NotBlank @Size(min = 2, max = 100) String companyName,
    @NotNull @DecimalMin(value = "0.01") BigDecimal budget
) {}

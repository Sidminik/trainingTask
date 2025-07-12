package co.avbinvest.companyservices.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CompanyWithUsersDTO(
    @Min(1) Long id,
    @NotBlank @Size(min = 2, max = 100) String companyName,
    @NotNull @DecimalMin(value = "0.01") BigDecimal budget,
    @Valid List<UserDTO> users
){}

package co.avbinvest.companyservices.dto.response;

import jakarta.validation.constraints.*;

public record UserDTO (
    @Min(1) Long id,
    @NotBlank @Size(min = 2, max = 50) String firstName,
    @NotBlank @Size(min = 2, max = 50) String lastName,
    @NotBlank @Pattern(regexp = "^\\+?[0-9\\-\\s]+$") String phoneNumber
){}

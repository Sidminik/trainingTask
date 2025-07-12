package co.avbinvest.userservices.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UserRequestDTO (
    @NotBlank @Size(min = 2, max = 50) String firstName,
    @NotBlank @Size(min = 2, max = 50) String lastName,
    @NotBlank @Pattern(regexp = "^\\+?[0-9\\-\\s]+$") String phoneNumber,
    @Valid @NotNull Long companyId
){}

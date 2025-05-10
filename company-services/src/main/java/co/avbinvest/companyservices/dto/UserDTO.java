package co.avbinvest.companyservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;
}

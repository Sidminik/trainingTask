package co.avbinvest.userservices.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    private CompanyDTO companyDTO;
}

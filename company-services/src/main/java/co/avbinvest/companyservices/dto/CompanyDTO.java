package co.avbinvest.companyservices.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String companyName;
    private BigDecimal budget;
    private List<UserDTO> users;
}

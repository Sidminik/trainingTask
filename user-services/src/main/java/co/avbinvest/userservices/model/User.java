package co.avbinvest.userservices.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9\\s-]{8,20}$")
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @NotNull
    @Column(nullable = false)
    private Long companyId;
}

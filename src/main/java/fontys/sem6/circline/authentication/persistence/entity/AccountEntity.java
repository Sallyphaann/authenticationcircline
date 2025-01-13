package fontys.sem6.circline.authentication.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@Entity
@Table(name = "account")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "userId")
    private Long userId;
    @NotBlank
    @Length(min = 2, max = 20)
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Length(max = 100)
    private String password;
    @NotBlank
    @Length(min = 2, max = 20)
    @Column(name = "role_name")
    private String role;

}

package fontys.sem6.circline.authentication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken {
    private String subject;
    private String roles;
    private Long userId;

    @JsonIgnore
    public boolean hasRole(String roleName) {
        if (roles == null) {
            return false;
        }
        return roles.contains(roleName);

    }
}

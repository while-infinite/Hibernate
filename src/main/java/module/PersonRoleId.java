package module;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PersonRoleId implements Serializable {
    private Long personId;
    private Long roleId;
}

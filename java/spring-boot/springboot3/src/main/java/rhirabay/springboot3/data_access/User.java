package rhirabay.springboot3.data_access;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
// javaxのパッケージ利用では起動に失敗する...
//import javax.persistence.Entity;
//import javax.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "Users")
public class User {
    @Id
    private String id;
    private String name;
}

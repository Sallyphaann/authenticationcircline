package fontys.sem6.circline.authentication.persistence;
import fontys.sem6.circline.authentication.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<AccountEntity,Long>  {
    AccountEntity findByEmail(String email);
    AccountEntity findByUserId(long id);
}

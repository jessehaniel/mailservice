
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMailRepository extends JpaRepository<Mail, Long> {

}

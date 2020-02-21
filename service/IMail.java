
import org.springframework.stereotype.Service;

import br.com.novio.mailservice.mailservice.model.Mail;

@Service
public interface IMail {
	
	Mail send(Mail email);
}

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "TB_ADRESSMAIL")
public class AddressMail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_address")
	private Long id;
	
	private String enderecoEmail;
	
	private boolean state;
	
	private String error;
	
	private LocalDateTime dataEnvio;

	public AddressMail() {}

	public AddressMail(String enderecoEmail, boolean state, String error, LocalDateTime dataEnvio) {
		super();
		this.enderecoEmail = enderecoEmail;
		this.state = state;
		this.error = error;
		this.dataEnvio = dataEnvio;
	}


	public Long getId() {
		return id;
	}

	public String getEnderecoEmail() {
		return enderecoEmail;
	}

	public String getError() {
		return error;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEnderecoEmail(String enderecoEmail) {
		this.enderecoEmail = enderecoEmail;
	}

	public void setError(String error) {
		this.error = error;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="America/Sao_Paulo")
	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(LocalDateTime dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	
}

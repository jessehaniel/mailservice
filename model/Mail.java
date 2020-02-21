
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_MAIL")
public class Mail {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_mail;
	
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_mail")
	@Valid
	private List<AddressMail> destinatarios = new ArrayList<>();;
	
	@NotBlank
	private String remetente;
	
	@NotBlank
	private String assunto;

	@NotBlank
	@Column(columnDefinition = "LONGTEXT")
	private String mensagem;
	
	//String
	private String nameFile;

	public Mail() {
		
	}
	
	public Mail(List<AddressMail> destinatarios, String remetente, String assunto,
			String mensagem, LocalDateTime dataEnvio, String nameFile) {
		this.destinatarios = destinatarios;
		this.remetente = remetente;
		this.assunto = assunto;
		this.mensagem = mensagem;
		this.setNameFile(nameFile);
	}

	public Long getId_mail() {
		return id_mail;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public void setId_mail(Long id_mail) {
		this.id_mail = id_mail;
	}

	public List<AddressMail> getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(List<AddressMail> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setAnexo(String nameFile) {
		this.setNameFile(nameFile);
	}

	public String getNameFile() {
		return nameFile;
	}

	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
}
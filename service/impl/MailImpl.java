
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;


import net.bytebuddy.asm.Advice.Return;

@Service
public class MailImpl implements IMail{

	@Autowired
	private JavaMailSender javaMailSender;

	private final IMailRepository mailRepository;
	String ext[] = {"png", "doc", "docx", "txt"}; 

	@Autowired
	private Environment env;

	public MailImpl(IMailRepository mailRepository) {
		this.mailRepository = mailRepository;
	}

	public Mail send(Mail email) {
		MimeMessage message = javaMailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(email.getAssunto());
			helper.setText(email.getMensagem(), true);

			if (isValidateExtension(email.getNameFile())) {
				getFileFTP(email.getNameFile());
				helper.addAttachment(email.getNameFile(), this.getFile(email.getNameFile()));
			}

			for(int i = 0; i< email.getDestinatarios().size(); i++) {

				email.getDestinatarios().get(i).setDataEnvio(LocalDateTime.now());

				try {
					helper.setTo(email.getDestinatarios().get(i).getEnderecoEmail());
					javaMailSender.send(message);

				}catch (Exception e) {
					email.getDestinatarios().get(i).setState(true);
					email.getDestinatarios().get(i).setError((e.getMessage()));
					continue;
				}
			}

		}catch (Exception e){

		}

		mailRepository.save(email);
		return email;
	}

	private void getFileFTP(String fileAnexo) {

		String files[] = null;
		FTPClient ftp = new FTPClient();
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		int reply = 0;

		try {

			if(Arrays.stream(env.getActiveProfiles()).anyMatch(
					env -> (env.equalsIgnoreCase("dev") || env.equalsIgnoreCase("hml")) )) 
			{
				ftp.connect("00.0.0.00");
				ftp.login("hml", "000000");

			}
			else if(Arrays.stream(env.getActiveProfiles()).anyMatch(
					env -> (env.equalsIgnoreCase("prod")) )) 
			{
				ftp.connect("xxxxxxxxxxxxxxxxxxxxxxxxxx");
				ftp.login("xxxxxx", "xxxxxxxx");
			}

			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				throw new Exception("Exception in connecting to FTP Server");
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			ftp.changeWorkingDirectory ("/usr/local/files/anexo/");
			files = ftp.listNames();
			for(String fileName : files) {
				if (fileName.equals(fileAnexo)) {
					try (
							FileOutputStream fos = new FileOutputStream("/tmp/"+fileName)) 
					{
						ftp.retrieveFile(fileName, fos);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if (ftp.isConnected()) {
				try {
					ftp.logout();
					ftp.disconnect();
				} catch (IOException e) {
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getFile(String nameFile) throws FileNotFoundException {

		File file = ResourceUtils.getFile("/tmp/" + nameFile);
		return file;
	}

	public boolean isValidateExtension(String anexo) {

		if ((anexo != null) && ("".equals(anexo.trim()) && Arrays.stream(ext).anyMatch(anexo.split("\\.")::equals))) {
			return true;
		}

		return false;
	}
}
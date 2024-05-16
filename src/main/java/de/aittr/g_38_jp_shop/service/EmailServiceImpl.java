package de.aittr.g_38_jp_shop.service;

import de.aittr.g_38_jp_shop.domain.entity.User;
import de.aittr.g_38_jp_shop.repository.UserRepository;
import de.aittr.g_38_jp_shop.service.interfaces.ConfirmationService;
import de.aittr.g_38_jp_shop.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender sender;

    private Configuration mailConfig;

    private ConfirmationService service;
    @Value("${spring.mail.username}")
    private String senderEmail;


    public EmailServiceImpl(JavaMailSender sender, Configuration mailConfig, ConfirmationService service) {
        this.sender = sender;
        this.mailConfig = mailConfig;
        this.service = service;

        mailConfig.setDefaultEncoding("UTF-8");
        mailConfig.setTemplateLoader(
                new ClassTemplateLoader(EmailServiceImpl.class.getClassLoader(),
                        "/mail/")
        );
    }

    @Override
    public void sendConfirmationEmail(User user) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,"UTF-8");

        String text = generateConfirmationEmail(user);

        try{
            helper.setFrom(senderEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Registration");
            helper.setText(text, true);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        sender.send(message);

    }


    private String generateConfirmationEmail(User user) {
        try {
            Template template = mailConfig.getTemplate(
                    "confirm_registretion_mail.ftlh");
            String code = service.generateConfirmationCode(user);


            Map<String, Object> model = new HashMap<>();
            model.put("name", user.getUsername());
            String url = "http://localhost:8080/register?code=" + code;
            model.put("link", url);

            return FreeMarkerTemplateUtils
                    .processTemplateIntoString(template, model);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

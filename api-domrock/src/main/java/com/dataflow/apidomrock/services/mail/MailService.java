package com.dataflow.apidomrock.services.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MailService {

    @Autowired
    private  JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendToken(String para, String org, String pass) throws MessagingException {

        String[] cc = {"joao.lamao@fatec.sp.gov.br", "eduardo.paula01@fatec.sp.gov.br", "ana.machado28@fatec.sp.gov.br", "andre.oliveira138@fatec.sp.gov.br", "lucas.oliveira321@fatec.sp.gov.br", "beatriz.bonatto@fatec.sp.gov.br", "naiara.santos3@fatec.sp.gov.br"};

        String orgAux = "";

        if (org != null){
            orgAux = " e " + org;
        }

        String envio = "<html>" +
                "<body style='font-family: Arial, sans-serif; font-size: 15px;'>" +
                "<h3 style='color: #007bff;'>Olá, " + para + "!</h3>" +
                "<p>Estamos muito animados em tê-lo(a) a bordo nesta parceria junto a <b>Dom Rock"+orgAux+"</b>!</p>" +
                "<p>Para completar o seu registro, precisamos de apenas mais um passo: ativar sua conta.</p>" +
                "<p>Aqui está o seu Token de Ativação:</p>" +
                "<p style='font-weight: bold;'>" + pass + "</p>" +
                "<p>Para ativar sua conta, copie e cole este token na página de ativação. Não se preocupe, é super fácil!</p>" +
                "<p>Se precisar de alguma ajuda ou tiver alguma dúvida, nossa equipe de suporte está sempre aqui.<br>Basta responder a este e-mail e ficaremos felizes em auxilia-lo.</p>" +
                "<p>Estamos ansiosos para ver você dentro da nossa comunidade!</p>" +
                "<p>Atenciosamente,<br/>Equipe DataFlow</p>" +
                "</body>" +
                "</html>";

        MimeMessage msg = javaMailSender.createMimeMessage();
        msg.setContent(envio, "text/html; charset=utf-8");
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
        helper.setTo(para);
        helper.setSubject("Token de acesso - DataFlow");
        helper.setText(envio, true);
        helper.setFrom(from);
        helper.setCc(cc);

        CompletableFuture.runAsync(() -> {
            javaMailSender.send(msg);
        });
    }

    public void enviarEmailComAnexo(String para, String titulo, String conteudo, String arquivo) throws MessagingException {
        var mensagem = javaMailSender.createMimeMessage();

        var helper = new MimeMessageHelper(mensagem, true);

        helper.setTo(para);
        helper.setSubject(titulo);
        helper.setText(conteudo, true);

        helper.addAttachment("image1.jpeg", new ClassPathResource(arquivo));

        javaMailSender.send(mensagem);
    }
}


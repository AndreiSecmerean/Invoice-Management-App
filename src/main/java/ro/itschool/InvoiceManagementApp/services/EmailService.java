package ro.itschool.InvoiceManagementApp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

@Component
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String message, String csvReport) throws MessagingException {
        log.info("Sending email to: " + to);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);

        byte[] csvReportBytes = csvReport.getBytes();
        ByteArrayDataSource byteArrayDataSource = new ByteArrayDataSource(csvReportBytes, "text/csv");
        mimeMessageHelper.addAttachment("report.csv", byteArrayDataSource);
        this.javaMailSender.send(mimeMessage);
        log.info("Sent email to: " + to);
    }
}

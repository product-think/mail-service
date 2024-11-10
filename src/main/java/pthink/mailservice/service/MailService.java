package pthink.mailservice.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pthink.mailservice.dto.MailLogDto;
import pthink.mailservice.entity.MailLog;
import pthink.mailservice.repository.MailLogRepository;
import pthink.mailservice.utility.SU;

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final MailLogRepository mailLogRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;

    public void send(MailLogDto mailLog) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailLog.getAddressFrom());
            helper.setTo(mailLog.getAddressTo());

            if (SU.notEmpty(mailLog.getAddressCc())) {
                helper.setCc(mailLog.getAddressCc());
            }

            if (SU.notEmpty(mailLog.getAddressBcc())) {
                helper.setBcc(mailLog.getAddressBcc());
            }

            helper.setSubject(mailLog.getSubject());
            helper.setText(mailLog.getBody());

            javaMailSender.send(message);

        } catch(Exception e) {
            log.error(e.getMessage());
            mailLog.setSendFailure(true);
        }

        Date now = new Date();
        mailLog.setSystemEnable(true);
        mailLog.setSystemCreateDate(now);
        mailLog.setSystemUpdateDate(now);
        this.mailLogRepository.saveAndFlush(modelMapper.map(mailLog, MailLog.class));
    }
}

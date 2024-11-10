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

import java.util.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final MailLogRepository mailLogRepository;
    private final ModelMapper modelMapper;
    private final JavaMailSender javaMailSender;

    public void send(MailLogDto mailLogDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailLogDto.getAddressFrom());
            helper.setTo(mailLogDto.getAddressTo());
            helper.setSubject(mailLogDto.getSubject());
            helper.setText(mailLogDto.getBody());

            javaMailSender.send(message);

        } catch(Exception e) {
            log.error(e.getMessage());
            mailLogDto.setSendFailure(true);
        }

        Date now = new Date();
        mailLogDto.setSystemEnable(true);
        mailLogDto.setSystemCreateDate(now);
        mailLogDto.setSystemUpdateDate(now);
        this.mailLogRepository.saveAndFlush(modelMapper.map(mailLogDto, MailLog.class));
    }
}

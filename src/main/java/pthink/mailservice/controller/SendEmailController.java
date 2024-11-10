package pthink.mailservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pthink.mailservice.dto.AccountDto;
import pthink.mailservice.dto.MailLogDto;
import pthink.mailservice.service.AccountService;
import pthink.mailservice.service.MailService;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "send-mail")
@Slf4j
public class SendEmailController {

    private final AccountService accountService;
    private final MailService mailService;

    @PostMapping("/")
    @ResponseBody
    public boolean sendEmail(
            HttpServletRequest request,
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("body") String body) {
        String remoteAddr = this.getRemoteAddr(request);
        log.info(remoteAddr);

        AccountDto account = accountService.findByIpAddress(remoteAddr);
        if (account == null) {
            log.error("account null");
            return true;
        }

        MailLogDto mailLog = new MailLogDto();
        mailLog.setSubject(title);
        mailLog.setBody(body);
        mailLog.setAddressFrom(account.getLoginId());
        mailLog.setAddressTo(email);
        mailLog.setAddressBcc(account.getLoginId());
        this.mailService.send(mailLog);

        return false;
    }

    //IPアドレス取得
    private String getRemoteAddr(HttpServletRequest request) {
        String xForwardedFor =  request.getHeader("X-Forwarded-For");
        //ELB等を経由していたらxForwardedForを返す
        if (xForwardedFor != null) {
            return xForwardedFor;
        }
        return request.getRemoteAddr();
    }
}

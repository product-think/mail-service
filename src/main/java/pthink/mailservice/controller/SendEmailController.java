package pthink.mailservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            HttpServletResponse response,
            @RequestParam("email") String email,
            @RequestParam("title") String title,
            @RequestParam("body") String body) {

        //リクエストのヘッダー情報の遷移元URLを取得
        String requestHeaderReferer = request.getHeader("REFERER");
        log.info("requestHeaderReferer:" + requestHeaderReferer);
        String[] refererArray = requestHeaderReferer.split("/");
        if (3 > refererArray.length) {
            log.error("referer fail");
            return true;
        }
        String referer = refererArray[0] + "//"+ refererArray[2];
        log.info("referer:" + referer);
        AccountDto account = accountService.findByReferer(referer);
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

        response.addHeader("Access-Control-Allow-Origin", "*");

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

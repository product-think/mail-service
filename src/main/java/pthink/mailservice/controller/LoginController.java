package pthink.mailservice.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pthink.mailservice.service.AccountService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/sign-in")
    public ModelAndView index(
            ModelAndView mav, HttpSession session,
            @RequestParam(value = "error", required = false) String error) {

        mav.addObject("showErrorMsg", false);
        if (error != null) {
            if (session != null) {
                AuthenticationException ex = (AuthenticationException) session
                        .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                if (ex != null) {
                    mav.addObject("showErrorMsg", true);
                    mav.addObject("errorMsg", "メールアドレスまたはパスワードが誤っています。");
                }
            }
        }

        mav.setViewName("sign-in");

        return mav;
    }
}

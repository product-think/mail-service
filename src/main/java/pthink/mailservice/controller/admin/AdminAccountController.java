package pthink.mailservice.controller.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pthink.mailservice.dto.AccountDto;
import pthink.mailservice.service.AccountService;
import pthink.mailservice.utility.SU;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "admin/account")
@Slf4j
public class AdminAccountController {
    private final AccountService accountService;

    @GetMapping(value = "/")
    public ModelAndView account(
            Principal principal, ModelAndView mav) {

        mav.setViewName("admin/account");
        return mav;
    }

    @GetMapping(value = "/find")
    @ResponseBody
    public List<AccountDto> findLoginId(
            Principal principal,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "referer") String referer) {
        return this.accountService.find(name, email, referer);
    }

    @GetMapping(value = "/find-login-id")
    @ResponseBody
    public AccountDto findLoginId(
            Principal principal,
            @RequestParam(name = "loginId") String loginId) {
        return this.accountService.findByLoginId(loginId);
    }

    @Transactional
    @PostMapping(value = "/")
    @ResponseBody
    public AccountDto update(
            Principal principal,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "loginId") String loginId,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "authRole") String authRole,
            @RequestParam(name = "referer") String referer
    ) {

        AccountDto account;
        boolean isNew = SU.empty(id);

        // NOTE: 初期登録の場合
        if (isNew) {
            account = new AccountDto();
            account.setPassword(password);
        }

        // NOTE: 更新の場合
        else {
            account = this.accountService.findById(Long.valueOf(id));
        }
        account.setName(name);
        account.setLoginId(loginId);
        account.setRoles(authRole);
        account.setReferer(referer);
        AccountDto newAccount = this.accountService.update(account); // NOTE: アカウント情報更新
        return newAccount;
    }

    @Transactional
    @PostMapping(value = "/password")
    @ResponseBody
    public boolean password(
            Principal principal,
            @RequestParam(name = "id") String id,
            @RequestParam(name = "password") String password
    ) {
        AccountDto newAccount = this.accountService.updatePassword(Long.valueOf(id), password);

        return true;
    }

    @Transactional
    @DeleteMapping(value = "/")
    @ResponseBody
    public boolean delete(
            Principal principal,
            @RequestParam(name = "id") String id
    ) {
        AccountDto newAccount = this.accountService.updateSystemEnable(Long.valueOf(id), false);

        return true;
    }
}

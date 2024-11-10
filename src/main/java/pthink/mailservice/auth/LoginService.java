package pthink.mailservice.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pthink.mailservice.entity.Account;
import pthink.mailservice.repository.AccountRepository;
import pthink.mailservice.utility.SU;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        if (SU.empty(loginId)) {
            throw new NullPointerException();
        }
        log.debug("loadUserByUsername(loginId):[{}]", loginId);

        Optional<Account> optionalAdminAccount = accountRepository.findBySystemEnableAndLoginId(true, loginId);
        if (optionalAdminAccount.isEmpty()) {
            throw new UsernameNotFoundException("Account not found by loginId:" + loginId);
        }

        return new LoginUser(optionalAdminAccount.get());
    }
}

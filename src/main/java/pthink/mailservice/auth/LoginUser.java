package pthink.mailservice.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pthink.mailservice.entity.Account;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoginUser extends User {
    private Account account;
    public LoginUser(Account account) {
        super(account.getLoginId(), account.getPassword(), account.getSystemEnable(),
                true, true, true,
                convertGrantedAuthorities(account.getRoles()));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    static Set<GrantedAuthority> convertGrantedAuthorities(String roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }
        Set<GrantedAuthority> authorities = Stream.of(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return authorities;
    }
}

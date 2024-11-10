package pthink.mailservice.utility;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.Authentication;
import pthink.mailservice.auth.LoginUser;
import pthink.mailservice.dto.AccountDto;

import java.security.Principal;

@RequiredArgsConstructor
public class AU {
    public static AccountDto getAccount(Principal principal) {
        if (null == principal) return null;
        LoginUser loginUser = (LoginUser)(((Authentication)principal).getPrincipal());
        return getAccountDto(loginUser);
    }
    public static AccountDto getAccount(Authentication authentication) {
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        return getAccountDto(loginUser);
    }
    private static AccountDto getAccountDto(LoginUser loginUser) {
        ModelMapper modelMapper = new ModelMapper();
        // マッチング戦略を厳しいものに設定
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // 型の完全マッチなど
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        // nullをスキップする設定
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(loginUser.getAccount(), AccountDto.class);
    }

}

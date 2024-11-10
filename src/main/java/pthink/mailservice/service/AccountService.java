package pthink.mailservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pthink.mailservice.dto.AccountDto;
import pthink.mailservice.entity.Account;
import pthink.mailservice.repository.AccountRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountDto findById(Long accountId) {
        Optional<Account> optional = this.accountRepository.findById(accountId);
        if (optional.isEmpty()) {
            return null;
        }
        AccountDto dto = this.modelMapper.map(optional.get(), AccountDto.class);
        return dto;
    }

    public AccountDto update(AccountDto dto) {
        Account entity = this.modelMapper.map(dto, Account.class);
        Date now = new Date();

        if (null == dto.getId()) {
            entity.setPassword(this.passwordEncoder.encode(dto.getPassword()));
            entity.setSystemEnable(true);
            entity.setSystemCreateDate(now);
        }
        entity.setSystemUpdateDate(now);

        Account newEntity = this.accountRepository.saveAndFlush(entity);

        AccountDto newDto = this.modelMapper.map(newEntity, AccountDto.class);
        return newDto;
    }

    public AccountDto updatePassword(Long accountId, String password) {
        Optional<Account> optional = this.accountRepository.findById(accountId);
        if (optional.isEmpty()) {
            return null;
        }
        Account entity = optional.get();
        entity.setPassword(this.passwordEncoder.encode(password));
        entity.setSystemUpdateDate(new Date());

        Account newEntity = this.accountRepository.saveAndFlush(entity);
        AccountDto newDto = this.modelMapper.map(newEntity, AccountDto.class);
        return newDto;
    }

    public AccountDto updateSystemEnable(Long accountId, boolean enable) {
        Optional<Account> optional = this.accountRepository.findById(accountId);
        if (optional.isEmpty()) {
            return null;
        }
        Account entity = optional.get();
        entity.setSystemEnable(enable);
        entity.setSystemUpdateDate(new Date());

        Account newEntity = this.accountRepository.saveAndFlush(entity);
        AccountDto newDto = this.modelMapper.map(newEntity, AccountDto.class);
        return newDto;
    }

    public List<AccountDto> find(String name, String email, String referer) {
        List<Account> entities = this.accountRepository.find(
                "%" + name + "%", "%" + email + "%", "%" + referer + "%");
        if (null == entities || entities.isEmpty()) {
            return null;
        }
        List<AccountDto> dtos = entities.stream()
                .map(e -> this.modelMapper.map(e, AccountDto.class))
                .toList();
        return dtos;
    }

    public AccountDto findByLoginId(String loginId) {
        Optional<Account> optional = this.accountRepository.findBySystemEnableAndLoginId(true, loginId);
        if (optional.isEmpty()) {
            return null;
        }
        AccountDto dto = this.modelMapper.map(optional.get(), AccountDto.class);
        return dto;
    }

    public AccountDto findByReferer(String referer) {
        Optional<Account> optional = this.accountRepository.findBySystemEnableAndReferer(true, referer);
        if (optional.isEmpty()) {
            return null;
        }
        AccountDto dto = this.modelMapper.map(optional.get(), AccountDto.class);
        return dto;
    }
}

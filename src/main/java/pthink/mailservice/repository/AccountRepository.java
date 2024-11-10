package pthink.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pthink.mailservice.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findBySystemEnableAndLoginId(Boolean systemEnable, String loginId);
    Optional<Account> findBySystemEnableAndReferer(Boolean systemEnable, String referer);

    @Query (nativeQuery = true,
            value = "select a.* " +
                    "from ACCOUNT a " +
                    "where a.SYSTEM_ENABLE = 1 " +
                    "    and a.NAME like :name " +
                    "    and a.LOGIN_ID like :email " +
                    "    and a.REFERER like :referer")
    List<Account> find(@Param("name") String name, @Param("email") String email, @Param("referer") String referer);
}

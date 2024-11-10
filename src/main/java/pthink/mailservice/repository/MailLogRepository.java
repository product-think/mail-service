package pthink.mailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pthink.mailservice.entity.MailLog;

public interface MailLogRepository extends JpaRepository<MailLog, Long> {
}

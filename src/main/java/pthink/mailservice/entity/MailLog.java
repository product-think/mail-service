package pthink.mailservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Table(name = "MAIL_LOG")
@Entity
@Data
public class MailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ADDRESS_FROM")
    private String addressFrom;

    @Column(name = "ADDRESS_TO")
    private String addressTo;

    @Column(name = "ADDRESS_CC")
    private String addressCc;

    @Column(name = "ADDRESS_BCC")
    private String addressBcc;

    @Column(name = "SUBJECT")
    private String subject;

    @Lob
    @Column(name = "BODY")
    private String body;

    @Column(name = "STATUS_CODE")
    private Integer statusCode;

    @Column(name = "SEND_FAILURE")
    private Boolean sendFailure;

    @Column(name = "SYSTEM_ENABLE", nullable = false)
    private Boolean systemEnable;

    @Column(name = "SYSTEM_CREATE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemCreateDate;

    @Column(name = "SYSTEM_UPDATE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemUpdateDate;

}

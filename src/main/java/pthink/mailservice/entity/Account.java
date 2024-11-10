package pthink.mailservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Table(name = "ACCOUNT")
@Entity
@Data
@ToString(exclude = {"password"})
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLES")
    private String roles;

    @Column(name = "REFERER")
    private String referer;

    @Column(name = "SYSTEM_ENABLE", nullable = false)
    private Boolean systemEnable;

    @Column(name = "SYSTEM_CREATE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemCreateDate;

    @Column(name = "SYSTEM_UPDATE_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date systemUpdateDate;
}

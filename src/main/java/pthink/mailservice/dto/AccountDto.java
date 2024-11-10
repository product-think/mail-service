package pthink.mailservice.dto;

import lombok.Data;
import pthink.mailservice.utility.SU;

import java.util.Date;

@Data
public class AccountDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String roles;
    private String ipAddress;
    private Boolean authAdmin;
    private Boolean authCompany;
    private Boolean authCandidate;
    private Boolean authReferee;
    private Boolean systemEnable;
    private Date systemCreateDate;
    private Date systemUpdateDate;

    public boolean getAuthAdmin() {
        if (SU.empty(this.roles)) {
            return false;
        }
        if (this.roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }
}

package pthink.mailservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MailLogDto {
    private Long id;
    private String addressFrom;
    private String addressTo;
    private String addressCc;
    private String addressBcc;
    private String subject;
    private String body;
    private Integer statusCode;
    private Boolean sendFailure;
    private Boolean systemEnable;
    private Date systemCreateDate;
    private Date systemUpdateDate;
}

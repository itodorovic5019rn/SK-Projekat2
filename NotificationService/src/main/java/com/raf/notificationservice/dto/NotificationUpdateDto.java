package com.raf.notificationservice.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationUpdateDto {

    private String text;
    private String type;
    @Email(message = "Invalid email format.")
    private String emailTo;
    private String dateCreated;

    public NotificationUpdateDto() {
    }

    public boolean getParseDate(String sDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = sdf.parse(sDate);
        }catch(ParseException p){
            return false;
        }
        return true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}

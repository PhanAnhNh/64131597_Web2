package Nhom2.DoAnWeb.NTUScientistInfor.service;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class DateEditorConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Custom editor for java.sql.Date
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isEmpty()) {
                    setValue(null);
                    return;
                }
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = dateFormat.parse(text);
                    setValue(new Date(parsedDate.getTime()));
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid date format for " + text, e);
                }
            }

            @Override
            public String getAsText() {
                Date value = (Date) getValue();
                if (value == null) {
                    return "";
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(value);
            }
        });
    }
}
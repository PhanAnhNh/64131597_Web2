package Nhom2.DoAnWeb.NTUScientistInfor.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class DateFormatterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }

    private static class DateFormatter implements Formatter<Date> {

        private static final String DATE_PATTERN = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

        @Override
        public Date parse(String text, Locale locale) throws ParseException {
            if (text == null || text.isEmpty()) {
                return null;
            }
            return dateFormat.parse(text);
        }

        @Override
        public String print(Date object, Locale locale) {
            if (object == null) {
                return "";
            }
            return dateFormat.format(object);
        }
    }
}
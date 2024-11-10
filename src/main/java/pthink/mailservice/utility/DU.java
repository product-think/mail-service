package pthink.mailservice.utility;

import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
public class DU {
    public static Date parseDate(String dateStr) {
        if (null == dateStr || dateStr.isEmpty()) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateStr);

        } catch (ParseException e) {
            throw new RuntimeException("(DU/parseDate) Failed to parse date: " + dateStr, e);
        }
    }
}

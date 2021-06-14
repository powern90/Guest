package kw.kimkihong.assign3.vo;

import java.util.Calendar;

public class DateVO {
    private Calendar date;

    public DateVO(Calendar date) {
        this.date = date;
    }

    public Calendar getDate() {
        return this.date;
    }

    public String getDay() {
        switch ((this.date.get(Calendar.DAY_OF_WEEK))) {
            case 1:
                return "일";
            case 2:
                return "월";
            case 3:
                return "화";
            case 4:
                return "수";
            case 5:
                return "목";
            case 6:
                return "금";
            default:
                return "토";
        }
    }

    public String getNum() {
        return String.valueOf(this.date.get(Calendar.DATE));
    }
}

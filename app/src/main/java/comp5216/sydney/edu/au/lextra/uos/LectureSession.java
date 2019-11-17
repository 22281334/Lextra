package comp5216.sydney.edu.au.lextra.uos;

public class LectureSession {

    private String weekday;
    private String time;
    private String weeks;
    private String location;
    private String sessionString;

    public LectureSession() {}

    public LectureSession(String weekday, String time, String weeks, String location, String sessionString) {
        this.weekday = weekday;
        this.time = time;
        this.weeks = weeks;
        this.location = location;
        this.sessionString = sessionString;
    }

    public void parseLectureString(String sessStr) {
        this.sessionString = sessStr;
        if (!sessStr.contains("@") || !sessStr.contains("[") || !sessStr.contains("]")) {
            return;
        }
        String[] split = sessStr.split("@");
        location = split[1].trim();
        String first = split[0].trim();
        int start = first.indexOf('[');
        int end = first.indexOf(']');
        weeks = first.substring(start+1, end);
        first = first.substring(0, start).trim();
        split = first.split(" ");
        weekday = split[0].trim();
        time = split[1].trim();
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        if (weekday == null) {
            return sessionString;
        } else {
            return weekday + " " + time + " during " + weeks + " at " + location;
        }
    }
}

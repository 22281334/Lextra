package comp5216.sydney.edu.au.lextra.uos;

import java.util.ArrayList;

public class UoS {

    private int acYear = -1;
    private String uosCode = null;
    private String uosName = null;
    private String session = null;
    private String deliveryMode = null;
    private String campus = null;
    private String lectureString = null;
    private ArrayList<LectureSession> lectures = new ArrayList<>();
    private LectureSession lecture = null;

    public UoS() {}

    public UoS(String uosCode, String uosName, LectureSession lecture){
        this.uosCode = uosCode;
        this.uosName = uosName;
        this.lecture = lecture;
    }

    public UoS(int acYear, String uosCode, String uosName, String session, String deliveryMode, String campus, String lectureString) {
        this.acYear = acYear;
        this.uosCode = uosCode;
        this.uosName = uosName;
        this.session = session;
        this.deliveryMode = deliveryMode;
        this.campus = campus;
        this.lectureString = lectureString;
        String[] sessionStrings = lectureString.split("\\|");
        for (String sessStr : sessionStrings) {
            sessStr = sessStr.trim();
            if (sessStr.length() > 0) {
                if (sessStr.equals(")")) {
                    System.out.println(uosCode);
                }
                LectureSession lecSession = new LectureSession();
                lecSession.parseLectureString(sessStr);
                lectures.add(lecSession);
            }
        }
    }

    public int getAcYear() {
        return acYear;
    }

    public void setAcYear(int acYear) {
        this.acYear = acYear;
    }

    public String getUosCode() {
        return uosCode;
    }

    public void setUosCode(String uosCode) {
        this.uosCode = uosCode;
    }

    public String getUosName() {
        return uosName;
    }

    public void setUosName(String uosName) {
        this.uosName = uosName;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public ArrayList<LectureSession> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<LectureSession> lectures) {
        this.lectures = lectures;
    }

    public LectureSession getLecture() {
        return lecture;
    }

    public void setLecture(LectureSession lecture) {
        this.lecture = lecture;
    }

    @Override
    public String toString() {
        return acYear + "\t" + uosCode + "\t" + uosName + "\t" + session + "\t" + deliveryMode + "\t" + campus + "\t" + lectureString;
    }
}

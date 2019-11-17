package comp5216.sydney.edu.au.lextra.uos;

import java.util.ArrayList;

public class LectureSorter {

    private static ArrayList<LectureSession> sortLectures(ArrayList<LectureSession> lectures) {

        ArrayList<LectureSession> sorted = new ArrayList<>();

        for (LectureSession lecture : lectures) {

            int lectureFirstWeek = getFirstWeek(lecture.getWeeks());
            int lectureWeekday = getWeekdayInt(lecture.getWeekday());
            int lectureStartHour = getStartHour(lecture.getTime());

            boolean added = false;
            for (int i = 0; i < sorted.size(); i++) {

                LectureSession sortedLecture = sorted.get(i);
                int sortedFirstWeek = getFirstWeek(sortedLecture.getWeeks());
                int sortedWeekday = getWeekdayInt(sortedLecture.getWeekday());
                int sortedStartHour = getStartHour(sortedLecture.getTime());

                if (lectureFirstWeek < sortedFirstWeek) {
                    sorted.add(i, lecture);
                    added = true;
                } else if (lectureFirstWeek == sortedFirstWeek) {
                    if (lectureWeekday < sortedWeekday) {
                        sorted.add(i, lecture);
                        added = true;
                    }
                    else if (lectureWeekday == sortedWeekday) {
                        if (lectureStartHour <= sortedStartHour) {
                            sorted.add(i, lecture);
                            added = true;
                        }
                    }
                }
            }

            if (!added) {
                sorted.add(lecture);
            }
        }

        return sorted;
    }

    private static int getWeekdayInt(String weekday) {
        ArrayList<String> weekdays = new ArrayList<>();
        weekdays.add("Mon");
        weekdays.add("Tue");
        weekdays.add("Wed");
        weekdays.add("Thu");
        weekdays.add("Fri");
        weekdays.add("Sat");
        weekdays.add("Sun");
        int index = weekdays.indexOf(weekday);
        if (index == -1) {
            return Integer.MAX_VALUE;
        } else {
            return index;
        }
    }

    private static int getStartHour(String time) {
        try {
            return Integer.parseInt(time.split(":")[0]);
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    private static int getFirstWeek(String weeks) {
        System.out.print(weeks + " ");
        StringBuilder sb = new StringBuilder();
        boolean hasSeenNumber = false;
        boolean hasSeenNegative = false;
        for (char c : weeks.toCharArray()) {
            if (hasSeenNegative && !isDigit(c)) {
                break;
            }
            if (isDigitOrNegative(c)) {
                if (c == '-') {
                    hasSeenNegative = true;
                }
                if (c != '-') {
                    hasSeenNumber = true;
                }
                if (c == '-' && hasSeenNumber) {
                    break;
                }
                sb.append(c);
            } else {
                if (hasSeenNumber) {
                    break;
                }
            }
        }
        if (sb.length() == 0 || sb.toString().replaceAll("-", "").length() == 0) return Integer.MAX_VALUE;
        try {
            return Integer.parseInt(sb.toString());
        } catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

    private static boolean isDigit(char c) {
        int ascii = (int) c;
        return ascii > 47 && ascii < 58;
    }

    private static boolean isDigitOrNegative(char c) {
        int ascii = (int) c;
        return isDigit(c) || ascii == 45;
    }

}

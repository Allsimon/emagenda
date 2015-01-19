package mines.ales.agenda.api;

import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Select;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mines.ales.agenda.api.pojo.Course;
import mines.ales.agenda.api.pojo.Promotion;
import mines.ales.agenda.api.pojo.Student;

@EBean(scope = EBean.Scope.Singleton)
public class API_emagenda extends API_agenda {
    private final String SERVER = "http://webdfd.ema.fr/cybema/cgi-bin/cgiempt.exe?TYPE=";
    private final String ADDRESS_PROMOTIONS = "promos_txt";
    private final String ADDRESS_COURSES = "planning_txt";
    private final String ADDRESS_STUDENT = "eleves_txt";
    private final String PREFIX_START_TIME = "&DATEDEBUT=";
    private final String PREFIX_END_TIME = "&DATEFIN=";
    private final String PREFIX_KEY_VALUE = "&VALCLE=";
    private final String PREFIX_KEY_TYPE = "&TYPECLE=";
    private final String KEY_TYPE_PROMOTION = "p0cleunik";
    private final String KEY_TYPE_STUDENT = "evcleunik";
    private final SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHHmm");
    private final String TAG = "API_EMAGENDA";
    private List<Promotion> promotions = new Select().from(Promotion.class).execute();

    @Override
    @Background
    public void getAllPromotions() {
        if (promotions.size() == 0)
            try {
                InputStream inputStream = httpGet(SERVER + ADDRESS_PROMOTIONS);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s;
                String[] splitted;
                Promotion promotion;
                while ((s = bufferedReader.readLine()) != null) {
                    if (!"EOT".equals(s)) {
                        promotion = new Promotion();
                        splitted = s.split(";");
                        for (int i = 0; i < splitted.length; i = i + 2) {
                            switch (splitted[i]) {
                                case "P0":
                                    promotion.setApp_id(toLong(splitted[i + 1]));
                                    break;
                                case "NOM":
                                    promotion.setName(splitted[i + 1]);
                                    break;
                            }
                        }
                        promotions.add(promotion);
                    }
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        notifyPromotionsFound(promotions);
    }

    @Override
    @Background
    public void getAllStudents() {
        List<Student> students = new Select().from(Student.class).execute();
        if (students.size() == 0)
            try {
                InputStream inputStream = httpGet(SERVER + ADDRESS_STUDENT);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s;
                String[] splitted;
                Student student;
                long temps = System.currentTimeMillis();
                while ((s = bufferedReader.readLine()) != null) {
                    if (!"EOT".equals(s)) {
                        student = new Student();
                        splitted = s.split(";");
                        for (int i = 0; i < splitted.length; i = i + 2) {
                            switch (splitted[i]) {
                                case "EV":
                                    student.setApp_id(toLong(splitted[i + 1]));
                                    break;
                                case "NOM":
                                    student.setLastName(splitted[i + 1]);
                                    break;
                                case "PRENOM":
                                    student.setFirstName(splitted[i + 1]);
                                    break;
                                case "MEL":
                                    student.setEmail(splitted[i + 1]);
                                    break;
                                case "P0":
                                    student.setPromotion(getPromotionsByAppID(toLong(splitted[i + 1])));
                                    break;
                            }
                        }
                        students.add(student);
                    }
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        notifyStudentsFound(students);
    }

    @Override
    public void getAllCourses(Date startDate, Date endDate) {
        List<Course> courses = new Select().from(Student.class).execute();
        if (courses.size() == 0)
            try {
                InputStream inputStream = httpGet(SERVER + ADDRESS_COURSES);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s;
                String[] splitted;
                Course course;
                long time = System.currentTimeMillis();
                while ((s = bufferedReader.readLine()) != null) {
                    if (!"EOT".equals(s)) {
                        course = new Course();
                        splitted = s.split(";");
                        String date = null;
                        for (int i = 0; i < splitted.length; i = i + 2) {
                            switch (splitted[i]) {
                                case "DQS":
                                    course.setApp_id(toLong(splitted[i + 1]));
                                    break;
                                case "DATE":
                                    date = splitted[i + 1];
                                    break;
                                case "HD":
                                    course.setStartTime(parseDate(date + splitted[i + 1]));
                                    break;
                                case "HF":
                                    course.setEndTime(parseDate(date + splitted[i + 1]));
                                    break;
                                case "TYPE":
                                    if ("CONTROLE".equals(splitted[i + 1].trim()))
                                        course.setControle(true);
                                    break;
                                case "COURS":
                                    course.setName(splitted[i + 1]);
                                    break;
                                case "SALLE":
                                    course.setRoom(splitted[i + 1]);
                                    break;
                                case "GROUPE":
                                    // TODO
                                    // course.setGroup(splitted[i + 1]);
                                    break;
                                case "LANOTE":
                                    course.setNote(splitted[i + 1]);
                                    break;
                                case "P0":
                                    course.setPromotion(getPromotionsByAppID(toLong(splitted[i + 1])));
                                    break;
                            }
                        }
                        courses.add(course);
                    }
                }
                Log.e(TAG, System.currentTimeMillis() - time + "");
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        notifyCoursesFound(courses);
    }

    @Override
    public void getAllCoursesByPromotion(Promotion promotion, Date startDate, Date endDate) {
    }

    @Override
    public void getStudentByPromotion(Promotion promotion) {
    }

    @Override
    public void getAllCoursesByStudent(Student student, Date startDate, Date endDate) {
    }

    private long toLong(String string) {
        return Long.parseLong(string.trim());
    }

    private Promotion getPromotionsByAppID(long app_id) {
        if (promotions.size() == 0)
            getAllPromotions();
        for (Promotion promotion : promotions)
            if (promotion.getApp_id() == app_id)
                return promotion;
        return null;
    }

    private Date parseDate(String date) {
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SupposeBackground
    public InputStream httpGet(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        return conn.getInputStream();
    }
}

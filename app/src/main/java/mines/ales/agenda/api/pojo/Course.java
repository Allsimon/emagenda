package mines.ales.agenda.api.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "courses")
public class Course extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "room")
    private String room;
    @Column(name = "teacher")
    private String teacher;
    @Column(name = "section")
    private Group group;
    @Column(name = "note")
    private String note;
    @Column(name = "startTime")
    private Date startTime;
    @Column(name = "endTime")
    private Date endTime;
    @Column(name = "isControle")
    private boolean isControle;
    @Column(name = "promotion")
    private Promotion promotion;
    @Column(name = "app_id")
    private long app_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isControle() {
        return isControle;
    }

    public void setControle(boolean isControle) {
        this.isControle = isControle;
    }

    /**
     * Id field is already used by Activeandroid, app_id is the idea used by the CGI
     *
     * @return id
     */
    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", teacher='" + teacher + '\'' +
                ", group=" + group +
                ", note='" + note + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isControle=" + isControle +
                ", promotion=" + promotion +
                ", app_id=" + app_id +
                '}';
    }
}

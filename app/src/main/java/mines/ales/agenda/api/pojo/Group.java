package mines.ales.agenda.api.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "groups")
public class Group extends Model {
    @Column(name = "app_id")
    private long app_id;
    @Column(name = "name")
    private String name;

    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Group{" +
                "app_id=" + app_id +
                ", name='" + name + '\'' +
                '}';
    }
}

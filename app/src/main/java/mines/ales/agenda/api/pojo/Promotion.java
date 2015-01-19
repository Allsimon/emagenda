package mines.ales.agenda.api.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "promotions")
public class Promotion extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "app_id")
    private long app_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Promotion{" +
                "name='" + name + '\'' +
                ", app_id=" + app_id +
                '}';
    }
}

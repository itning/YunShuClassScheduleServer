package top.itning.yunshu.classscheduleserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 专业
 *
 * @author itning
 */
@Entity
public class Profession {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "pid")
    @GenericGenerator(name = "pid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    /**
     * 专业名
     */
    @Column(nullable = false, unique = true)
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profession{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

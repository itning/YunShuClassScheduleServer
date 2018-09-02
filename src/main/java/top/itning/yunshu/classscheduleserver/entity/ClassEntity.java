package top.itning.yunshu.classscheduleserver.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 班级信息
 *
 * @author itning
 */
@Entity
public class ClassEntity {
    /**
     * ID
     */
    @Id
    @GeneratedValue(generator = "ceid")
    @GenericGenerator(name = "ceid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    /**
     * 名
     */
    @Column(unique = true, nullable = false)
    private String name;
    /**
     * 版本
     */
    @Column(nullable = false)
    private String version;

    /**
     * 对应专业
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "profession", nullable = false)
    private Profession profession;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}

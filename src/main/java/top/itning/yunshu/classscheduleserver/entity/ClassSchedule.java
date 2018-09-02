package top.itning.yunshu.classscheduleserver.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 课程实体
 *
 * @author itning
 */
@Entity
public class ClassSchedule {
    /**
     * 课程编号
     */
    @Id
    @GeneratedValue(generator = "cid")
    @GenericGenerator(name = "cid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    /**
     * 星期几的课程
     */
    @Column(nullable = false)
    private int week;
    /**
     * 第几节课
     */
    @Column(nullable = false)
    private int section;
    /**
     * 课程名
     */
    @Column(nullable = false)
    private String name;
    /**
     * 地点
     */
    @Column(nullable = false)
    private String location;
    /**
     * 教师
     */
    private String teacher;
    /**
     * 所属班级
     */
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "byClass", nullable = false)
    private ClassEntity byClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public ClassEntity getByClass() {
        return byClass;
    }

    public void setByClass(ClassEntity byClass) {
        this.byClass = byClass;
    }
}

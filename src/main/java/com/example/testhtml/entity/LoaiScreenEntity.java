package com.example.testhtml.entity;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "loai_screen", schema = "world_phone", catalog = "")
public class LoaiScreenEntity extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "LoaiScreenEntity")
    private List<ScreenEntity> screenEntities;

    public LoaiScreenEntity() {
    }

    public LoaiScreenEntity(Long valueOf) {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScreenEntity> getScreenEntities() {
        return screenEntities;
    }

    public void setScreenEntities(List<ScreenEntity> screenEntities) {
        this.screenEntities = screenEntities;
    }
}



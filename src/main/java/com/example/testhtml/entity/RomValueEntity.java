package com.example.testhtml.entity;

import javax.persistence.*;


@Entity
@Table(name = "rom_value", schema = "world_phone", catalog = "")
public class RomValueEntity extends BaseEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "loai_rom", referencedColumnName = "ID")
    private LoaiRomEntity LoaiRomEntity;

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

    public com.example.testhtml.entity.LoaiRomEntity getLoaiRomEntity() {
        return LoaiRomEntity;
    }

    public void setLoaiRomEntity(com.example.testhtml.entity.LoaiRomEntity loaiRomEntity) {
        LoaiRomEntity = loaiRomEntity;
    }
}

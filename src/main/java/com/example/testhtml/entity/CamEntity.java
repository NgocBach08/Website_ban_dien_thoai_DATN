package com.example.testhtml.entity;

import javax.persistence.*;



@Entity
@Table(name = "cam", schema = "world_phone", catalog = "")
public class CamEntity extends BaseEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME_CAM_TRUOC")
    private String camTruoc;

    @Basic
    @Column(name = "NAME_CAM_SAU")
    private String camSau;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCamTruoc() {
        return camTruoc;
    }

    public void setCamTruoc(String camTruoc) {
        this.camTruoc = camTruoc;
    }

    public String getCamSau() {
        return camSau;
    }

    public void setCamSau(String camSau) {
        this.camSau = camSau;
    }
}

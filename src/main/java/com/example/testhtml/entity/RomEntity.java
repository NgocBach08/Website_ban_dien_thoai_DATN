package com.example.testhtml.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rom", schema = "world_phone", catalog = "")
public class RomEntity extends BaseEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "ID")
    private ProductEntity productEntity;

    @OneToMany(mappedBy = "romEntity")
    private List<ProductPropertyEntity> productProperties;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public List<ProductPropertyEntity> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(List<ProductPropertyEntity> productProperties) {
        this.productProperties = productProperties;
    }
}

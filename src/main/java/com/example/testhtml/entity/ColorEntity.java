package com.example.testhtml.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "color", schema = "world_phone")
public class ColorEntity extends BaseEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "value_color")
    private String valueColor;


    @OneToMany(mappedBy = "colorEntity")
    private List<ProductPropertyEntity> productProperties;

    @OneToMany(mappedBy = "colorEntity")
    private List<InvoiceOrderDetailEntity> invoiceOrderDetailEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueColor() {
        return valueColor;
    }

    public void setValueColor(String valueColor) {
        this.valueColor = valueColor;
    }

    public List<ProductPropertyEntity> getProductProperties() {
        return productProperties;
    }

    public void setProductProperties(List<ProductPropertyEntity> productProperties) {
        this.productProperties = productProperties;
    }

    public List<InvoiceOrderDetailEntity> getInvoiceOrderDetailEntities() {
        return invoiceOrderDetailEntities;
    }

    public void setInvoiceOrderDetailEntities(List<InvoiceOrderDetailEntity> invoiceOrderDetailEntities) {
        this.invoiceOrderDetailEntities = invoiceOrderDetailEntities;
    }
}

package com.example.testhtml.entity;

import javax.persistence.*;

/**
 * Description:
 *
 * @author: hieu
 * @since: 22/10/2022
 * Project_name: com.example.testhtml.entity
 */
@Entity
@Table(name = "imei", schema = "world_phone", catalog = "")
public class ImeiEntity extends BaseEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "STATUS")
    private String status;

    @Basic
    @Column(name = "VALUE")
    private String value;


    @Basic
    @Column(name = "PROPERTY_PRODUCT_ID")
    private Long  propertyProductId;

    @Basic
    @Column(name = "ORDER_DETAIL_ID")
    private Long orderDetailId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getPropertyProductId() {
        return propertyProductId;
    }

    public void setPropertyProductId(Long propertyProductId) {
        this.propertyProductId = propertyProductId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}

package com.example.testhtml.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "invoiceorder", schema = "world_phone", catalog = "")
public class InvoiceOrderEntity extends BaseEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "NOTE")
    private String note;

    @Basic
    @Column(name = "CODE_ORDER")
    private String codeOrder;

    @Basic
    @Column(name = "TOTAL_MONEY")
    private Long totalMoney;

    @Basic
    @Column(name = "GIAM_GIA")
    private Long giamGia;


    @Basic
    @Column(name = "TONG_TIEN_TRA")
    private Long totalMoneyPay;


    @Basic
    @Column(name = "TONG_TIEN_THUA")
    private Long totalMoneyThua;
    @Basic
    @Column(name = "STATUS")
    private Integer status;

    @Basic
    @Column(name = "RECEIVE_DATE")
    private Date receiveDate;

    @ManyToOne
    @JoinColumn(name = "SUPPLIER_ID", referencedColumnName = "ID")
    private SupplierEntity supplierEntity;

    @ManyToOne
    @JoinColumn(name = "STAFF_ID", referencedColumnName = "ID")
    private StaffEntity staffEntity;

    @OneToMany(mappedBy = "invoiceOrderEntity")
    private List<InvoiceOrderDetailEntity> detailEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }


    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public SupplierEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(SupplierEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }

    public StaffEntity getStaffEntity() {
        return staffEntity;
    }

    public void setStaffEntity(StaffEntity staffEntity) {
        this.staffEntity = staffEntity;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<InvoiceOrderDetailEntity> getDetailEntity() {
        return detailEntity;
    }

    public void setDetailEntity(List<InvoiceOrderDetailEntity> detailEntity) {
        this.detailEntity = detailEntity;
    }

    public Long getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(Long giamGia) {
        this.giamGia = giamGia;
    }


    public Long getTotalMoneyPay() {
        return totalMoneyPay;
    }

    public void setTotalMoneyPay(Long totalMoneyPay) {
        this.totalMoneyPay = totalMoneyPay;
    }

    public Long getTotalMoneyThua() {
        return totalMoneyThua;
    }

    public void setTotalMoneyThua(Long totalMoneyThua) {
        this.totalMoneyThua = totalMoneyThua;
    }
}

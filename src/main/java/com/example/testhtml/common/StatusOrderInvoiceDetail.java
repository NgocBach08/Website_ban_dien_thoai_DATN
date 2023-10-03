package com.example.testhtml.common;

/**
 * Description:
 *
 * @author: hieu
 * @since: 18/10/2022
 * Project_name: com.example.testhtml.common
 */
public enum StatusOrderInvoiceDetail {


    DANG_NHAP(1),
    DA_NHAP(2),
    NCC_HET_HANG(3);


    private final int index;

    StatusOrderInvoiceDetail(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

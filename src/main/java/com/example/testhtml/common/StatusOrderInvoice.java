package com.example.testhtml.common;

/**
 * Description:
 *
 * @author: hieu
 * @since: 11/10/2022
 * Project_name: com.example.testhtml.common
 */
public enum StatusOrderInvoice {
    HUY(0),
    DOI_DUYET(-1),
    DA_DAT(1),
    DA_NHAN(2),
    GIAO_THIEU(3);


    private final int index;

    StatusOrderInvoice(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

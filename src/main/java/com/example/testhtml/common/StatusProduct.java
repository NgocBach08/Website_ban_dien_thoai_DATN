package com.example.testhtml.common;

/**
 * Description:
 *
 * @author: hieu
 * @since: 22/10/2022
 * Project_name: com.example.testhtml.common
 */
public enum StatusProduct {
    DANG_KINH_DOANH("ON"),
    NGUNG_KINH_DOANH("OFF");

    private final String status;

    StatusProduct(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

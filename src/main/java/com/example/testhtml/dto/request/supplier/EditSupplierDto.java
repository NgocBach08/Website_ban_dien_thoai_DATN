package com.example.testhtml.dto.request.supplier;

import lombok.Data;

@Data
public class EditSupplierDto {
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String address;

    private String status;

    private String note;
}

package com.example.testhtml.dto.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 04/12/2022
 * Project_name: com.example.testhtml.dto.respone
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThongKeDto {
    private String img;
    private String nameProduct;
    private String romProduct;
    private String colorProduct;

    private String priceProduct;
    private String quantityDaBan;
}

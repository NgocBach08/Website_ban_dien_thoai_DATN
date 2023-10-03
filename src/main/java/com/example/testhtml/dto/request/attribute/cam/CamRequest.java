package com.example.testhtml.dto.request.attribute.cam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 03/07/2022
 * Project_name: com.example.testhtml.dto.request.attribute.cam
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamRequest {
    private Long id;
    private String nameTruoc;
    private String nameSau;
}
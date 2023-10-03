package com.example.testhtml.dto.request.imei;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 *
 * @author: hieu
 * @since: 22/10/2022
 * Project_name: com.example.testhtml.dto.request.imei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelRequest {
    private MultipartFile formData;
}

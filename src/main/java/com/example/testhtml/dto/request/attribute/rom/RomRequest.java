package com.example.testhtml.dto.request.attribute.rom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 07/07/2022
 * Project_name: com.example.testhtml.dto.request.attribute.rom
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RomRequest {
    private Long id;
    private String name;
    private Long loai;
    public long getLoaiRomId() {
        return loai;
    }
}

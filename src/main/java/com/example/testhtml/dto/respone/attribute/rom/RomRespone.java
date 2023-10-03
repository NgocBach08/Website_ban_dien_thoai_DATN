package com.example.testhtml.dto.respone.attribute.rom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: hieu
 * @since: 07/07/2022
 * Project_name: com.example.testhtml.dto.respone.attribute.rom
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RomRespone {
    private Long id;
    private String name;
    private Long loai;

    public RomRespone(Long id, String s) {
    }
}

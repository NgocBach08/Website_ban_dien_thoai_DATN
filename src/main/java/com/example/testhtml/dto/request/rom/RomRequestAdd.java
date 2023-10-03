package com.example.testhtml.dto.request.rom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RomRequestAdd {
    private String nameRom;
    private String status;
    private Long productId;
}

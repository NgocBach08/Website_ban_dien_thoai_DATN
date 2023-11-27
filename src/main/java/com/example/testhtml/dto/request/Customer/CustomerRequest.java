package com.example.testhtml.dto.request.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Description:
 *
 * @author: POLY_DuyDV
 * @version: 1.0
 * @since: 2/27/2022
 * Project_name: Tech-cam
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {

    private String id;
    @NotBlank
    @Pattern(regexp = "[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]{0,50}$")
    private String fullName;
    @NotBlank
//    @Pattern(regexp = "|[0][3245879]{1}[0-9]{8}\"")
    private String phoneNumber;
    @NotBlank
//    @Pattern(regexp = " \"^(.+)@(\\\\S+)$\"")
    private String email;
    @NotBlank
    private String address;
    //    @NotNull
    private String dateOfBirth;
}

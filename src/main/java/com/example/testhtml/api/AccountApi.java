package com.example.testhtml.api;

import com.example.testhtml.constant.ConstansErrorCode;
import com.example.testhtml.entity.StaffEntity;
import com.example.testhtml.exception.WorldPhoneExp;
import com.example.testhtml.service.impl.StaffServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AccountApi {



    private final StaffServiceImpl staffService;

    private String message;

    @GetMapping("/count_login_false")
    public ResponseEntity<String> getCountLoginFalse(@RequestParam("email") String email) {
        StaffEntity staff = staffService.getByEmail(email);
        if (staff != null) {

            // Check block account
            if (staff.getStatus().equals("0")) {
                message = String.valueOf(new WorldPhoneExp(ConstansErrorCode.LOGIN_ACCOUNT_BLOCKED).getErrorMessage().getVn());
                log.info(message);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
            }
            return ResponseEntity.status(HttpStatus.OK).body("OKE");
        }
        message = String.valueOf(new WorldPhoneExp(ConstansErrorCode.LOGIN_EMAIL_NOT_EXITS).getErrorMessage().getVn());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}

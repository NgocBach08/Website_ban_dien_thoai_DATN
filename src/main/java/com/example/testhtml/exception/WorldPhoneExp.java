package com.example.testhtml.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
public class WorldPhoneExp extends RuntimeException{

    private final ErrorMessage errorMessage;

    public WorldPhoneExp(String errorCode){
        ErrorMessage message = ErrorMessageLoader.getMessage(errorCode);
        if(Objects.isNull(message)){
            message = new ErrorMessage();
            message.setVn(errorCode);
        }
        this.errorMessage = message;
    }

    public WorldPhoneExp(String errorCode, Object... args){
        this.errorMessage = ErrorMessageLoader.getMessage(errorCode);
    }
}

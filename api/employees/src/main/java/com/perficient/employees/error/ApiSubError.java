package com.perficient.employees.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

abstract class ApiSubError {

}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

//    ApiValidationError(String object, String field, Object rejectedValue, String message) {
//        this.object = object;
//        this.message = message;
//        this.field = field;
//        this.rejectedValue = rejectedValue;
//    }
}

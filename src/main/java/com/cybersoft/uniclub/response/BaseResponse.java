package com.cybersoft.uniclub.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private int statusCode = 200;
    private  String message;
    private T data;
}

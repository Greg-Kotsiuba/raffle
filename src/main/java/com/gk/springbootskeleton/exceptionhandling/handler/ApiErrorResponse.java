package com.gk.springbootskeleton.exceptionhandling.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Common representation for all kinds of Service Error.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ApiErrorResponse {

    private String traceId;
    private Integer status;
    private String message;

}

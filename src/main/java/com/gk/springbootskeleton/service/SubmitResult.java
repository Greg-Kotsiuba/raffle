package com.gk.springbootskeleton.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Represent submit raffle result.
 * In successful case errors map is empty, and approved is TRUE.
 * <pre>{@code
 *     {
 *         "approved": true,
 *         "errors": {}
 *     }
 * }</pre>
 * In failed case error map contains invalid field value as key and problem description as value.
 * <pre>{@code
 *     {
 *         "approved": false,
 *         "errors" {
 *             "123$": "Code should be 5 digits long and contain only unique digits"
 *         }
 *     }
 * }</pre>
 */
@Data
@AllArgsConstructor
public
class SubmitResult {
    private Boolean approved;
    private Map<String, String> errors;
}

package org.bobrov.JobbyBobby.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}

package com.knzv.spring_boot_todo.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private String text;
    private String description;
    private boolean status;
    private LocalDate deadlineDate;
    private LocalDate creationDate;
}

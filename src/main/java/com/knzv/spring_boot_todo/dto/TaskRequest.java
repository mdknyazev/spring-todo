package com.knzv.spring_boot_todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {
    @NotNull
    private String text;
    private String description;
    private LocalDate deadlineDate;
}

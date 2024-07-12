package com.knzv.spring_boot_todo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubtaskResponse {
    private Long id;
    private String text;
    private boolean status;
    private Long taskParentId;
}

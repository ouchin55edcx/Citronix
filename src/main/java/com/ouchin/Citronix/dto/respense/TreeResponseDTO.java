package com.ouchin.Citronix.dto.respense;

import com.ouchin.Citronix.entity.Tree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeResponseDTO {

    private Long id;
    private LocalDate plantingDate;
    private boolean productive;

}



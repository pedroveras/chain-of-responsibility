package com.pedrosessions.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Expense {
    private BigDecimal amount;
    private LocalDateTime dateOfOccurence;
}

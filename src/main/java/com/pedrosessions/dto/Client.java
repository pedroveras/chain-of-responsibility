package com.pedrosessions.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Client {
    private String name;
    private String account;
    private String agency;
    private String segment;
    private LocalDateTime clientSince;
    private List<Expense> expenses;
}

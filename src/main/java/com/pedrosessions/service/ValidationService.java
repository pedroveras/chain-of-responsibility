package com.pedrosessions.service;

import com.pedrosessions.dto.Client;
import com.pedrosessions.rules.MonthlyExpensesRule;
import com.pedrosessions.rules.SegmentRule;
import com.pedrosessions.rules.TimeAsClientRule;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    public boolean validate(Client client) {

        TimeAsClientRule timeAsClientRule = new TimeAsClientRule();
        MonthlyExpensesRule monthlyExpensesRule = new MonthlyExpensesRule(timeAsClientRule);
        SegmentRule segmentRule = new SegmentRule(monthlyExpensesRule);

        return segmentRule.validate(client);
    }
}

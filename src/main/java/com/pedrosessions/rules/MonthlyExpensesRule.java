package com.pedrosessions.rules;

import com.pedrosessions.constants.CashbackParams;
import com.pedrosessions.dto.Client;
import com.pedrosessions.dto.Expense;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthlyExpensesRule implements Rule {

    private Rule nextRule;

    @Override
    public boolean validate(Client client) {
        BigDecimal expensesAmount = client.getExpenses().stream().filter(this::validatePeriod)
                .map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (expensesAmount.compareTo(CashbackParams.MONTHLY_EXPENSES) >= 0) {
            return nextRule.validate(client);
        }

        return false;
    }

    private boolean validatePeriod(Expense expense) {
        Month pastMonth = LocalDateTime.now().minusMonths(1L).getMonth();

        return expense.getDateOfOccurence().getMonth().equals(pastMonth);
    }
}

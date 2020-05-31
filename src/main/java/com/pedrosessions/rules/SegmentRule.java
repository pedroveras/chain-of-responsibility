package com.pedrosessions.rules;

import com.pedrosessions.constants.CashbackParams;
import com.pedrosessions.dto.Client;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SegmentRule implements Rule {

    private Rule nextRule;

    @Override
    public boolean validate(Client client) {
        if (client.getSegment().equals(CashbackParams.SEGMENT)) {
            return nextRule.validate(client);
        }

        return false;
    }
}

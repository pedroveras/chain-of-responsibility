package com.pedrosessions.rules;

import com.pedrosessions.constants.CashbackParams;
import com.pedrosessions.dto.Client;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeAsClientRule implements Rule {

    @Override
    public boolean validate(Client client) {
        return client.getClientSince().plusYears(CashbackParams.TIME_AS_CLIENT)
                .isBefore(LocalDateTime.now());
    }
}

package com.pedrosessions.rules;

import com.pedrosessions.dto.Client;

public interface Rule {
    boolean validate(Client client);
}

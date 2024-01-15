package com.stonedt.intelligence.service;

import com.stonedt.intelligence.entity.User;

public interface PopUpService {
    boolean needPopUp(User user);

    void close(User user);
}

package com.stonedt.intelligence.service.impl;

import com.stonedt.intelligence.dao.UserPopUpDao;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.entity.UserPopUp;
import com.stonedt.intelligence.service.PopUpService;
import org.springframework.stereotype.Service;

/**
 * @author 文轩
 */
@Service
public class PopUpServiceImpl implements PopUpService {

    private final UserPopUpDao userPopUpDao;

    public PopUpServiceImpl(UserPopUpDao userPopUpDao) {
        this.userPopUpDao = userPopUpDao;
    }

    @Override
    public boolean needPopUp(User user) {
        UserPopUp userPopUp = userPopUpDao.selectOne(user.getId());
        if (userPopUp == null) {
            userPopUpDao.insert(new UserPopUp(user.getId(), 0));
            return true;
        }
        return userPopUp.getCount() < 5;
    }

    @Override
    public void close(User user) {
        userPopUpDao.plusOne(user.getId());
    }
}

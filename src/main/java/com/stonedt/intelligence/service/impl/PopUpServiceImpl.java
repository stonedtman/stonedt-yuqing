package com.stonedt.intelligence.service.impl;

import com.stonedt.intelligence.dao.UserPopUpDao;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.entity.UserPopUp;
import com.stonedt.intelligence.service.PopUpService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 文轩
 */
@Service
public class PopUpServiceImpl implements PopUpService {

    private final UserPopUpDao userPopUpDao;

    private final StringRedisTemplate stringRedisTemplate;

    public PopUpServiceImpl(UserPopUpDao userPopUpDao,
                            StringRedisTemplate stringRedisTemplate) {
        this.userPopUpDao = userPopUpDao;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean needPopUp(User user) {
        // 从redis中获取
        String value = stringRedisTemplate.opsForValue().get("popUp:" + user.getId());
        if (value != null) {
            return false;
        }

        UserPopUp userPopUp = userPopUpDao.selectOne(user.getId());
        if (userPopUp == null) {
            userPopUpDao.insert(new UserPopUp(user.getId(), 0));
            return true;
        }
        return userPopUp.getCount() < 5;
    }

    @Override
    public void close(User user) {
        // 保存到redis中
        stringRedisTemplate.opsForValue().set("popUp:" + user.getId(), "1", 1, TimeUnit.DAYS);
        userPopUpDao.plusOne(user.getId());
    }

    @Override
    public boolean needContact(Long projectId, Integer total) {
        if (total > 50) {
            stringRedisTemplate.delete("PopUpContactMe:" + projectId);
            return false;
        }

        String need = stringRedisTemplate.opsForValue().get("PopUpContactMe:" + projectId);
        return need != null;
    }

    @Override
    public void closeContact(Long projectId) {
        stringRedisTemplate.delete("PopUpContactMe:" + projectId);
    }
}

package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.UserService;
import com.stonedt.intelligence.util.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description: 用户实现层 <br>
 * date: 2020/4/14 11:29 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

	@Value("${token.private-key}")
	private String privateKey;

    @Override
    public User selectUserByTelephone(String telephone) {
        return userDao.selectUserByTelephone(telephone);
    }

    @Override
    public Integer updateUserLoginCountByPhone(Map<String, Object> map) {
        return userDao.updateUserLoginCountByPhone(map);
    }

	@Override
	public Map<String, String> getUserById(Long user_id) {
		return userDao.getUserById(user_id);
	}

	@Override
	public boolean updateUserPwdById(Long user_id, String password) {
		return userDao.updateUserPwdById(user_id, password);
	}

	@Override
	public Boolean saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public Map<String, String> getqrcode(String telephone) {
		// TODO Auto-generated method stub
		return userDao.getqrcode(telephone);
	}

	@Override
	public User selectUserByopenid(String openid) {
		// TODO Auto-generated method stub
		return userDao.selectUserByopenid(openid);
	}

	@Override
	public int addapply(String openid, String name, String telephone, String industry, String company) {
		// TODO Auto-generated method stub
		return userDao.addapply(openid,name,telephone,industry,company);
	}

	@Override
	public Map<String, Object> selectUserApplyByopenid(String openid) {
		// TODO Auto-generated method stub
		return userDao.selectUserApplyByopenid(openid);
	}

	@Override
	public List<Map<String, Object>> getUserByorganizationid(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getUserByorganizationid(id);
	}

	@Override
	public Map<String, Object> getUserInfoById(Long user_id) {
		// TODO Auto-generated method stub
		return userDao.getUserInfoById(user_id);
	}

	@Override
	public void setAlloffline() {
		userDao.setAlloffline();
	}

	@Override
	public List<Map<String, Object>> getAllcommentator(Long user_id) {
		// TODO Auto-generated method stub
		return userDao.getAllcommentator(user_id);
	}

	@Override
	public Map<String, Object> onlinestatistical(User user) {
		return userDao.onlinestatistical(user.getUser_id());
	}

	@Override
	public void updateLoginFailCountAndTime(Map<String, Object> mapParam) {
		userDao.updateLoginFailCountAndTime(mapParam);
	}

	@Override
	public void editIs_change_pas(Long userId) {
		userDao.editIs_change_pas(userId);
	}

	@Override
	public void updateEndLoginTime(Long userId) {
		userDao.updateEndLoginTime(userId);
	}

	@Override
	public String getToken(User user) throws Exception {
		user.setPassword(null);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		// 设置token签发时间
		userDTO.setTokenIssueTime(System.currentTimeMillis());
        return JWTUtils.createJWT(JSON.toJSONString(userDTO, SerializerFeature.WriteMapNullValue), privateKey);
	}
}

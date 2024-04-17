package com.stonedt.intelligence.service;

import com.nimbusds.jose.JOSEException;
import com.stonedt.intelligence.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description: 用户层 <br>
 * date: 2020/4/14 11:28 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
public interface UserService {
    User selectUserByTelephone(String telephone);

    Integer updateUserLoginCountByPhone(Map<String,Object> map);
    
    Map<String, String> getUserById(Long user_id);
    
    boolean updateUserPwdById(Long user_id, String password);
    void editIs_change_pas(Long userId);
    Boolean saveUser(User user);

	Map<String, String> getqrcode(String telephone);

	User  selectUserByopenid(String openid);

	int addapply(String openid, String name, String telephone, String industry, String company);

	Map<String, Object> selectUserApplyByopenid(String openid);

    List<Map<String, Object>> getUserByorganizationid(Integer id);

    Map<String, Object> getUserInfoById(Long user_id);

    void setAlloffline();

    List<Map<String, Object>> getAllcommentator(Long user_id);

    Map<String, Object> onlinestatistical(User user);


    void updateLoginFailCountAndTime(Map<String, Object> mapParam);

    void updateEndLoginTime(Long userId);

    /**
     * 生成用户token
     * @param user 用户信息
     * @return token
     */
    String getToken(User user) throws JOSEException;

    /**
     * 获取长期token
     */
    String getLongToken(User user) throws JOSEException;

    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    User selectUserByUserId(Long userId);

    int updatePassword( Long userId, String password);

    User getUserByUserId(Long userId);
}

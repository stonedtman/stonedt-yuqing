package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.vo.BindParamsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * description: 用户实现层 <br>
 * date: 2020/4/14 13:54 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
@Mapper
public interface UserDao {
    /**
     * @param [telephone,手机号码]
     * @return com.stonedt.intelligence.entity.User
     * @description: 根据手机号查用户是否存在 <br>
     * @version: 1.0 <br>
     * @date: 2020/4/14 13:56 <br>
     * @author: huajiancheng <br>
     */

    User selectUserByTelephone(@Param("telephone") String telephone);

    /**
     * @param [map,参数列表，手机号，登录次数等]
     * @return java.lang.Integer
     * @description: updateUserLoginCountByPhone <br>
     * @version: 1.0 <br>
     * @date: 2020/4/14 13:56 <br>
     * @author: huajiancheng <br>
     */

    Integer updateUserLoginCountByPhone(@Param("map") Map<String, Object> map);
    
    /**
     * 查询用户信息
     * @param user_id
     * @return
     */
    Map<String, String> getUserById(@Param("user_id")Long user_id);
    
    /**
     * 修改密码
     * @param user_id
     * @param password
     * @return
     */
    boolean updateUserPwdById(@Param("user_id")Long user_id,@Param("password")String password);
    
    boolean updateUseropenidById(@Param("user_id")Long user_id,@Param("openid")String openid);
    
    Boolean saveUser(User user);

	boolean updateUseropenidstatusById(@Param("openid")String openid);

	/**
	 * 获取绑定微信公众号的用户列表
	 * @return
	 */
	List<User> getUserByWechatUser();

	List<User> getAllUser();

	boolean addticket(@Param("telephone")String telephone, @Param("ticket")String ticket);

	Map<String, String> getqrcode(@Param("telephone")String telephone);

	User selectUserByopenid(@Param("openid")String openid);

	/**
	 * 申请试用
	 * @param openid
	 * @param name
	 * @param telephone
	 * @param industry
	 * @param company
	 * @return
	 */
	int addapply(@Param("openid")String openid, @Param("name")String name, @Param("telephone")String telephone, 
			@Param("industry")String industry, 
			@Param("company")String company);

	/**
	 * 判断当前用户是否已经申请过
	 * @param openid
	 * @return
	 */
	Map<String, Object> selectUserApplyByopenid(@Param("openid")String openid);

	List<Map<String, Object>> getUserByorganizationid(@Param("id")Integer id);

	Map<String, Object> getUserInfoById(@Param("user_id")Long user_id);

	void updateOnline(User user);

	void setAlloffline();

	List<Map<String, Object>> getAllcommentator(@Param("user_id")Long user_id);
	
	
	
	List<User> getAllUserNotDelete();


	Map<String, Object> onlinestatistical(@Param("user_id")Long user_id);

	void updateLoginFailCountAndTime(Map<String, Object> mapParam);

	void editIs_change_pas(Long userId);

	void updateEndLoginTime(Long userId);

	/**
	 * 绑定nlp
	 * @param bindParamsVo 绑定参数
	 */
    void bindNlp(BindParamsVo bindParamsVo);

	/**
	 * 绑定写作宝
	 * @param bindParamsVo 绑定参数
	 */
	void bindXie(BindParamsVo bindParamsVo);

	User selectById(Integer id);

	/**
	 * 根据openid查询用户信息
	 * @param openid 微信openid
	 * @return 用户信息
	 */
    User selectUserByOpenid(String openid);

	/**
	 * 保存邮件配置
	 * @param id 用户id
	 * @param mailJson 邮件配置json
	 */
    void saveMailConfig(@Param("id") Integer id, @Param("mailJson") String mailJson);

	/**
	 * 根据用户id查询邮件配置
	 * @param userId
	 */
	String selectMailJsonByUserId(Long userId);

	/**
	 * 根据用户更新用户登录状态
	 * @param id 用户id
	 */
	void updateUserLoginCountById(Integer id);

	/**
	 * 根据用户id更新用户登录状态
	 * @param openId
	 */
	void updateUserWechatFlagByOpenid(@Param("openId") String openId,@Param("wechatFlag") Integer wechatFlag);

    User selectUserByUserId(Long userId);

    String selectOpenidByUserId(Long userId);

    int updatePassword(@Param("userId") Long userId, @Param("password") String password);

    User getUserByUserId(@Param("userId") Long userId);

	void updateOpenidById(@Param("id")int id, @Param("openid")String openid);
}

package com.r7.core.uim.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.r7.core.uim.dto.UimUserUpdateDTO;
import com.r7.core.uim.dto.UserSignUpDTO;
import com.r7.core.uim.model.UimUser;
import com.r7.core.uim.vo.UimUserVO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务层
 *
 * @author zhongpingli
 */
public interface UimUserService extends IService<UimUser> {


    /**
     * 用户注册
     *
     * @param code          邀请码
     * @param userSignUpDTO 用户注册信息
     * @param ip            注册IP
     * @return 返回注册信息
     */
    UimUserVO signUpUser(String code, UserSignUpDTO userSignUpDTO, String ip);

    /**
     * 修改用户信息
     *
     * @param id               用户ID
     * @param uimUserUpdateDTO 修改用户信息
     * @param userId           操作用户ID
     * @return 返回修改后的用户信息
     */
    UimUserVO updateUser(Long id, UimUserUpdateDTO uimUserUpdateDTO, Long userId);

    /**
     * 修改头像
     *
     * @param id     用户ID
     * @param avatar 用户修改后头像
     * @return 算法修改
     */
    boolean updateUserAvatar(Long id, String avatar);


    /**
     * 换绑手机号
     *
     * @param id          用户ID
     * @param phoneNumber 手机号
     * @param code        手机验证码
     * @return 是否成功
     */
    boolean bindUserPhone(Long id, Long phoneNumber, Long code);


    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param code        验证码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    boolean updateUserPassword(Long id, Long code, String oldPassword, String newPassword);

    /**
     * 根据用户id获取用户信息
     *
     * @param id 用户ID
     * @return 返回用户信息
     */
    UimUserVO getUserById(Long id);

    /**
     * 根据邀请码查询用户信息
     *
     * @param code 邀请码
     * @return 返回用户信息
     */
    UimUserVO getUserByCode(String code);

    /**
     * 分页展示用户
     *
     * @param search   搜索条件
     * @param organId  组织ID
     * @param pageNum  当前页数
     * @param pageSize 每页展示个数
     * @return 返回信息
     */
    IPage<UimUserVO> pageUser(String search, Long organId, int pageNum, int pageSize);

    /**
     * 分页展示用户
     *
     * @param search   搜索条件
     * @param pageNum  当前页数
     * @param pageSize 每页展示个数
     * @return 返回信息
     */
    IPage<UimUserVO> pageUser(String search, int pageNum, int pageSize);


    /**
     * 根据电话号获取用户信息
     *
     * @param phone 电话号
     * @return 返回用户信息
     */
    UimUserVO getUserByPhone(Long phone);


    /**
     * 发送用户注册验证码
     *
     * @param phone        发送电话号
     * @param templateCode 短信模板
     */
    void sendSmsCode(Long phone, String templateCode);
}

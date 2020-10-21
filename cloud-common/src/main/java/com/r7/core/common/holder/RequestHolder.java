package com.r7.core.common.holder;

import com.r7.core.common.model.UserInfo;

/**
 * 使用threadLocal 保存当前线程用户
 *
 * @author zhongpingli
 */
public class RequestHolder {

    private static final ThreadLocal<UserInfo> userHolder = new ThreadLocal<UserInfo>();

    public static void addUserInfo(UserInfo userInfo) {
        userHolder.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return userHolder.get();
    }

    public static long getUserId() {
        return userHolder.get().getId();
    }

    public static long getOrganId() {
        return userHolder.get().getOrganId();
    }

    public static long getAppId() {
        return userHolder.get().getAppId();
    }

    public static void removeUserInfo() {
        userHolder.remove();
    }


}

package com.r7.core.common.holder;

import com.r7.core.common.model.UserInfo;

/**
 * 使用threadLocal 保存当前线程用户
 *
 * @author zhongpingli
 */
public class RequestHolder {

    private static final ThreadLocal<UserInfo> USER_HOLDER = new ThreadLocal<UserInfo>();

    public static void addUserInfo(UserInfo userInfo) {
        USER_HOLDER.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return USER_HOLDER.get();
    }

    public static long getUserId() {
        return USER_HOLDER.get().getId();
    }

    public static long getOrganId() {
        return USER_HOLDER.get().getOrganId();
    }

    public static long getAppId() {
        return USER_HOLDER.get().getAppId();
    }

    public static void removeUserInfo() {
        USER_HOLDER.remove();
    }


}

package com.r7.core.stand.video.agora.sample;

import com.r7.core.stand.video.agora.media.RtcTokenBuilder;
import com.r7.core.stand.video.agora.media.RtcTokenBuilder.Role;

public class RtcTokenBuilderSample {
    static String appId = "c02f35742ed94202b4b8fe2f91101da6";
    static String appCertificate = "6d900e3978e44155b4dc360546eaf67e";
    static String channelName = "test";
    static String userAccount = "2082341273";
    static int uid = 0;
    static int expirationTimeInSeconds = 3600;

    public static void main(String[] args) throws Exception {
        RtcTokenBuilder token = new RtcTokenBuilder();
        int timestamp = (int)(System.currentTimeMillis() / 1000 + expirationTimeInSeconds);
//        String result = token.buildTokenWithUserAccount(appId, appCertificate,
//        		 channelName, userAccount, Role.Role_Publisher, timestamp);
//        System.out.println(result);
        
        String result = token.buildTokenWithUid(appId, appCertificate,
       		 channelName, uid, Role.Role_Publisher, timestamp);
        System.out.println(result);
    }
}

package com.r7.core.stand.video.agora;

import java.util.TimerTask;

/**
 * @author muyongliang
 * @date 2020/10/29
 * @description RecordingCleanTimer
 */
public class RecordingCleanTimer extends TimerTask {
    AgoraRecordingEventHandler agoraRecordingEventHandler;

    public RecordingCleanTimer(AgoraRecordingEventHandler agoraRecordingEventHandler) {
        this.agoraRecordingEventHandler = agoraRecordingEventHandler;
    }

    @Override
    public void run() {
        agoraRecordingEventHandler.clean();
    }
}
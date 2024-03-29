package com.punuo.sys.app.agedcare.model;

/**
 * Created by chenblue23 on 2016/6/1.
 */
public class Msg {
    private String msgId;
    private String fromUserId;
    private String toUserId;
    private int time;
    private int isTimeShow;
    private String content;
    private int type;
    private int progress;
    private float recordtime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(float recordtime) {
        this.recordtime = recordtime;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setIsTimeShow(int isTimeShow) {
        this.isTimeShow = isTimeShow;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public int getTime() {
        return time;
    }

    public int getIsTimeShow() {
        return isTimeShow;
    }

    public String getContent() {
        return content;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && o.getClass() == Msg.class) {
            Msg msg = (Msg) o;
            if (this.getMsgId().equals(msg.getMsgId())) {
                return true;
            }
        }
        return false;
    }
}

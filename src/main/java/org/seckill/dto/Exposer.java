package org.seckill.dto;

/**
 * 暴露秒杀地址DTO
 * <p>User: yeyaohui
 * <p>Date: Jun 25, 2016
 * <p>Version: 1.0
 */
public class Exposer {
    /**
     * 是否开启秒杀
     */
    private boolean exposed;

    /**
     * 秒杀ID
     */
    private long secKillId;

    /**
     * 一种加密措施
     */
    private String md5;

    /**
     *系统当前时间（毫秒值）
     */
    private long now;

    private long start;

    private long end;

    public Exposer(boolean exposed, String md5, long secKillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.secKillId = secKillId;
    }

    public Exposer(boolean exposed, long secKillId, long now, long start, long end) {
        this.exposed = exposed;
        this.now = now;
        this.start = start;
        this.secKillId = secKillId;
        this.end = end;
    }

    public Exposer(boolean exposed, long secKillId) {
        this.exposed = exposed;
        this.secKillId = secKillId;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public long getSecKillId() {
        return secKillId;
    }

    public void setSecKillId(long secKillId) {
        this.secKillId = secKillId;
        this.secKillId = secKillId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", secKillId=" + secKillId +
                ", md5='" + md5 + '\'' +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

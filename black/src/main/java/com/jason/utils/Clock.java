package com.jason.utils;

import java.util.Date;

/**
 * Created by fuyongde on 2016/8/31.
 */
public interface Clock {
    enum Type {
        MILLIS, SECOND, MINUTE, HOUR, DAY
    }

    Clock DEFAULT = new DefaultClock();

    /**
     * 获取当前时间
     * @return
     */
    Date getCurrentDate();

    /**
     * 获取当前的毫秒数
     * @return
     */
    long getCurrentTimeInMillis();

    /**
     * 获取时间戳
     * @param type
     * @return
     */
    long getTimestamp(Type type);


    /**
     * 默认时间提供者，返回当前的时间，线程安全。
     */
    class DefaultClock implements Clock {

        @Override
        public Date getCurrentDate() {
            return new Date();
        }

        @Override
        public long getCurrentTimeInMillis() {
            return System.currentTimeMillis();
        }

        @Override
        public long getTimestamp(Type type) {
            long timestamp = 0;
            switch (type) {
                case MILLIS:
                    timestamp = getCurrentTimeInMillis();
                    break;
                case SECOND:
                    timestamp = getCurrentTimeInMillis() / 1000;
                    break;
                case MINUTE:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60);
                    break;
                case HOUR:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60 * 60);
                    break;
                case DAY:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60 * 60 * 24);
                    break;
                default:
                    break;
            }
            return timestamp;
        }
    }

    /**
     * 可配置的时间提供者，用于测试.
     */
    class MockClock implements Clock {

        private long time;

        public MockClock() {
            this(0);
        }

        public MockClock(Date date) {
            this.time = date.getTime();
        }

        public MockClock(long time) {
            this.time = time;
        }

        @Override
        public Date getCurrentDate() {
            return new Date(time);
        }

        @Override
        public long getCurrentTimeInMillis() {
            return time;
        }

        @Override
        public long getTimestamp(Type type) {
            long timestamp = 0;
            switch (type) {
                case MILLIS:
                    timestamp = getCurrentTimeInMillis();
                    break;
                case SECOND:
                    timestamp = getCurrentTimeInMillis() / 1000;
                    break;
                case MINUTE:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60);
                    break;
                case HOUR:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60 * 60);
                    break;
                case DAY:
                    timestamp = getCurrentTimeInMillis() / (1000 * 60 * 60 * 24);
                    break;
                default:
                    break;
            }
            return timestamp;
        }

        /**
         * 重新设置日期。
         */
        public void update(Date newDate) {
            time = newDate.getTime();
        }

        /**
         * 重新设置时间。
         */
        public void update(long newTime) {
            this.time = newTime;
        }

        /**
         * 滚动时间.
         */
        public void increaseTime(int millis) {
            time += millis;
        }

        /**
         * 滚动时间.
         */
        public void decreaseTime(int millis) {
            time -= millis;
        }
    }
}

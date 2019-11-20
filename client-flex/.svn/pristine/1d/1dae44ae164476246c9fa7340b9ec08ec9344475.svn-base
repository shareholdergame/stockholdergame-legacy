package com.stockholdergame.client.util {
    import flash.utils.Timer;

    public class DateUtil {
        public function DateUtil() {
        }

        public static function isToday(d:Date):Boolean {
            var currDate:Date = new Date();
            var interval:Number = currDate.getTime() - d.getTime();
            return interval < 86400000 && interval >= 0;
        }

        public static function delay(sec:int):void {
            var timer:Timer = new Timer(sec * 1000, 1);
            timer.start();
        }
    }
}

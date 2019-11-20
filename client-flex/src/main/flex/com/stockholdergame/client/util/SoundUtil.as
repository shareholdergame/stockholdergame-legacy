package com.stockholdergame.client.util {
    import flash.media.Sound;

    public class SoundUtil {
        public function SoundUtil() {
        }

        public static function playSound(soundClass:Class):void {
            var sound:Sound = new soundClass();
            sound.play();
        }
    }
}

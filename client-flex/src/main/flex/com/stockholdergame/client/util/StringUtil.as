package com.stockholdergame.client.util {
    import mx.utils.StringUtil;

    public class StringUtil {
        public function StringUtil() {
        }

        public static function capitalizeFirstLetter(s:String):String {
            return s.charAt(0).toUpperCase() + s.substring(1);
        }

        public static function isBlank(s:String):Boolean {
            return s == null || mx.utils.StringUtil.trim(s).length == 0;
        }

        public static function valueIn(value:String, values:Array):Boolean {
            if (values == null || values.length == 0) {
                return false;
            }
            for each (var obj:String in values) {
                if (value == obj) {
                    return true;
                }
            }
            return false;
        }
    }
}

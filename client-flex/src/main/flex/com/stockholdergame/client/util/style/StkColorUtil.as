package com.stockholdergame.client.util.style {
    import com.stockholdergame.client.util.collection.CollectionUtil;

    import mx.collections.ArrayCollection;
    import mx.styles.StyleManager;

    public class StkColorUtil {
        public function StkColorUtil() {
        }

        public static const moveOrderBackgroundColorMapping:ArrayCollection =
                CollectionUtil.createCollectionFromArray(["#e9cecd", "#d5e1f2", "#dee9d4", "#f0e1c9", "#e3d2dc", "#d7d2e8"]);

        public static const moveOrderColorMapping:ArrayCollection =
                CollectionUtil.createCollectionFromArray(["#8b0000", "#00008b", "#006400", "#e9aa26", "magenta", "violet"]);

        public static function invertColor(colorName:String):uint {
            var blackColor:uint = StyleManager.getColorName('black');
            var whiteColor:uint = StyleManager.getColorName('white');
            if (colorName == null) {
                return blackColor;
            }
            var color:uint = StyleManager.getColorName(colorName);
            if (blackColor - color > color - whiteColor) {
                return whiteColor;
            } else {
                return blackColor;
            }
        }
    }
}

package com.stockholdergame.client.ui.skins {
import flash.display.CapsStyle;
    import flash.display.GradientType;
    import flash.display.JointStyle;
import flash.display.LineScaleMode;
    import flash.display.SpreadMethod;
    import flash.geom.Matrix;

import mx.skins.RectangularBorder;

    public class PlayButtonSkin extends ButtonSkin {

        public function PlayButtonSkin() {
            super();
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            handleSkinName();
            drawSkin(unscaledWidth, unscaledHeight);
            super.updateDisplayList(unscaledWidth, unscaledHeight);
        }


        override protected function drawSkin(unscaledWidth:Number, unscaledHeight:Number):void {
            var m:Matrix = new Matrix();
            m.createGradientBox(unscaledWidth, (unscaledHeight * 3) / 4, Math.PI / 2);
            var radius:int = unscaledHeight - 1;
            radius = radius > 0 ? radius : 0;
            graphics.clear();
            graphics.beginGradientFill(GradientType.LINEAR, fillColors, [1, 1], [0, 255], m, SpreadMethod.REFLECT);
            graphics.lineStyle(borderThickness, lineColor, 1, true, LineScaleMode.NONE, CapsStyle.NONE, JointStyle.ROUND);
            graphics.drawRoundRect(0, 0, unscaledWidth, unscaledHeight, radius);
            graphics.endFill();
        }
    }
}

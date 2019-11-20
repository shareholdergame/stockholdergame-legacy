package com.stockholdergame.client.ui.skins {

    import flash.display.CapsStyle;
    import flash.display.DisplayObject;
    import flash.display.GradientType;
    import flash.display.JointStyle;
    import flash.display.LineScaleMode;
    import flash.display.SpreadMethod;
    import flash.geom.Matrix;

    import mx.skins.RectangularBorder;

    public class ButtonSkin extends RectangularBorder {

        protected var lineColor:uint;
        protected var fillColors:Array;
        protected var borderThickness:uint = 1;

        public function ButtonSkin() {
            super();
            initDefaultStyle();
        }

        protected function initDefaultStyle():void {
            lineColor = 0xABB6F6;
            fillColors = [0xB8BFE7, 0x929CD1];
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            handleSkinName();
            drawSkin(unscaledWidth, unscaledHeight);
            super.updateDisplayList(unscaledWidth, unscaledHeight);
        }

        protected function drawSkin(unscaledWidth:Number, unscaledHeight:Number):void {
            var m:Matrix = new Matrix();
            m.createGradientBox(unscaledWidth, (unscaledHeight * 3) / 4, Math.PI / 2);

            graphics.clear();
            graphics.beginGradientFill(GradientType.LINEAR, fillColors, [1, 1], [0, 255], m, SpreadMethod.REFLECT);
            graphics.lineStyle(borderThickness, lineColor, 1, true, LineScaleMode.NONE, CapsStyle.NONE, JointStyle.ROUND);
            graphics.drawRoundRect(0, 0, unscaledWidth, unscaledHeight, 7);
            graphics.endFill();
        }

        protected function handleSkinName():void {
            switch (name) {
                case "downSkin":
                    lineColor = 0xABB6F6;
                    fillColors = [0xABB6F6, 0xF6F2FF];
                    break;
                case "overSkin":
                    lineColor = 0xABB6F6;
                    fillColors = [0xF6F2FF, 0xABB6F6];
                    break;
                case "disabledSkin":
                    lineColor = 0xABB6F6;
                    fillColors = [0xC5C5C5, 0xC5C5C5];
                    break;
            }
        }
    }
}

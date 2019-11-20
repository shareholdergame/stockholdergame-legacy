package com.stockholdergame.client.ui.skins {
    import flash.display.CapsStyle;
    import flash.display.GradientType;
    import flash.display.JointStyle;
    import flash.display.LineScaleMode;
    import flash.display.SpreadMethod;
    import flash.geom.Matrix;

    import mx.skins.ProgrammaticSkin;

    public class ComboBoxArrowSkin extends ProgrammaticSkin {
        private var fillColors:Array;

        public function ComboBoxArrowSkin() {
            super();
            initDefaultStyle();
        }

        protected function initDefaultStyle():void {
            fillColors = [0xB8BFE7, 0x929CD1];
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            handleSkinName();
            var m:Matrix = new Matrix();
            m.createGradientBox(unscaledWidth, (unscaledHeight * 3) / 4, Math.PI / 2);

            graphics.clear();
            graphics.beginGradientFill(GradientType.LINEAR, fillColors, [1, 1], [0, 255], m, SpreadMethod.REFLECT);
            var borderThickness:uint = 1;
            var lineColor:uint = 0x495B9C;
            graphics.lineStyle(borderThickness, lineColor, 1, true, LineScaleMode.NONE, CapsStyle.NONE, JointStyle.ROUND);
            graphics.drawRoundRect(0, 0, unscaledWidth, unscaledHeight, 7);
            graphics.endFill();

            var arrowColor:uint = 0xF6F2FF;
            graphics.beginFill(arrowColor);
            graphics.moveTo(unscaledWidth - 12, unscaledHeight / 2 + 4);
            graphics.lineTo(unscaledWidth - 16, unscaledHeight / 2 - 4);
            graphics.lineTo(unscaledWidth - 8, unscaledHeight / 2 - 4);
            graphics.lineTo(unscaledWidth - 12, unscaledHeight / 2 + 4);
            graphics.endFill();
        }

        protected function handleSkinName():void {
            switch (name) {
                case "downSkin":
                    fillColors = [0xABB6F6, 0xF6F2FF];
                    break;
                case "overSkin":
                    fillColors = [0xF6F2FF, 0xABB6F6];
                    break;
                case "disabledSkin":
                    fillColors = [0xC5C5C5, 0xC5C5C5];
                    break;
            }
        }
    }
}

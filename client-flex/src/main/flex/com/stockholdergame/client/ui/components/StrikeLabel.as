package com.stockholdergame.client.ui.components {
    import flash.text.TextLineMetrics;

    import mx.controls.Label;

    [Style(name="strokePosition", type="String", enumeration="over,under,center", inherit="no")]
    [Style(name="strokeThickness", type="Number", inherit="no", format="Length")]
    public class StrikeLabel extends Label {

        public function StrikeLabel() {
            super();
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            super.updateDisplayList(unscaledWidth, unscaledHeight);

            if (textField) {
                var metrics:TextLineMetrics = textField.getLineMetrics(0);
                var strokePosition:String = this.getStyle("strokePosition");
                var strokeThickness:Number = this.getStyle("strokeThickness");
                var c:Number = strokePosition == "center" ? 0.66 : strokePosition == "over" ? 0 : 1;
                var y:int = ( metrics.ascent * c ) + (strokePosition == "under" ? 4 : 2);

                if (textField.text.length > 0) {
                    graphics.clear();
                    graphics.lineStyle(strokeThickness, getStyle("color"), 1);
                    var x0:int = this.getStyle("textAlign") == "right" ? unscaledWidth - metrics.width - 2 : 1;
                    var x1:int = this.getStyle("textAlign") == "right" ? unscaledWidth : metrics.width + 3;
                    graphics.moveTo(x0 , y);
                    graphics.lineTo(x1, y);
                }
            }
        }
    }
}

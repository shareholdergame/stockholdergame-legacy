package com.stockholdergame.client.ui.components {
    import mx.controls.ToolTip;

    public class HTMLToolTip extends ToolTip {
        public function HTMLToolTip() {
            super();
        }


        override protected function commitProperties():void {
            super.commitProperties();
            textField.htmlText = text;
        }
    }
}

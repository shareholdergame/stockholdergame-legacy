package com.stockholdergame.client.ui.message {
    import flash.utils.ByteArray;

    [Bindable]
    public class Message {
        public function Message(messageKey:String, messageSeverity:MessageSeverity, icon:ByteArray = null, userName:String = null, args:Array = null) {
            this._messageKey = messageKey;
            this._messageSeverity = messageSeverity;
            this._args = args;
            this._icon = icon;
            this._userName = userName;
        }

        private var _userName:String;
        private var _args:Array;
        private var _messageKey:String;
        private var _messageSeverity:MessageSeverity;
        private var _icon:ByteArray;

        public function get icon():ByteArray {
            return _icon;
        }

        public function get imageSource():Class {
            return _messageSeverity.imageSource;
        }

        public function get messageSeverity():MessageSeverity {
            return _messageSeverity;
        }

        public function get messageKey():String {
            return _messageKey;
        }

        public function get args():Array {
            return _args;
        }

        public function get styleName():String {
            return _messageSeverity.styleName;
        }

        public function get userName():String {
            return _userName;
        }
    }
}

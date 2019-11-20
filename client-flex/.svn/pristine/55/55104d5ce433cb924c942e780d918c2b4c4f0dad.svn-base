package com.stockholdergame.client.model.session {

    import flash.utils.Dictionary;

    public class Session {

        public function Session() {
        }

        private var sessionAttributes:Dictionary = new Dictionary();

        public function getAttribute(key:SessionAttribute):Object {
            return sessionAttributes[key];
        }

        public function setAttribute(data:Object, key:SessionAttribute):void {
            sessionAttributes[key] = data;
        }

        public function isAttributeNotNull(key:SessionAttribute):Boolean {
            return sessionAttributes[key] != null;
        }

        public function clear():void {
            sessionAttributes = new Dictionary();
        }
    }
}
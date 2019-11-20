package com.stockholdergame.client.ui.events {

    import flash.events.Event;

    public class BusinessEvent extends Event {

        public function BusinessEvent(type:String, data:Object = null, bubbles:Boolean = false, cancelable:Boolean = false) {
            super(type, bubbles, cancelable);
            this.data = data;
        }

        public var data:Object;

        override public function clone():Event {
            return new BusinessEvent(type,  data);
        }
    }
}

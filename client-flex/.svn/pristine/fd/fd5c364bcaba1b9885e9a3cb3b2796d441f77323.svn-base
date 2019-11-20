package com.stockholdergame.client.ui.events {
    import flash.events.Event;

    public class CardEvent extends Event {
        public function CardEvent(type:String, cardId:Number) {
            super(type);
            this.cardId = cardId;
        }

        public static const CARD_APPLIED:String = "cardApplied";
        public static const CARD_ROLLED_BACK:String = "cardRolledBack";

        public var cardId:Number;

        override public function clone():Event {
            return new CardEvent(type, cardId);
        }
    }
}

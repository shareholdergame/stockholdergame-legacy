package com.stockholdergame.client.ui.events {
    import com.stockholdergame.client.model.dto.game.BuySellOperation;

    import flash.events.Event;

    public class BuySellShareEvent extends Event {

        public function BuySellShareEvent(type:String, value:int, buySellOperation:BuySellOperation) {
            super(type);
            this.value = value;
            this.buySellOperation = buySellOperation;
        }

        public static const BUY_SELL_SHARE:String = "buySellShare";

        public var value:int;
        public var buySellOperation:BuySellOperation;

        override public function clone():Event {
            return new BuySellShareEvent(type, value, buySellOperation);
        }
    }
}

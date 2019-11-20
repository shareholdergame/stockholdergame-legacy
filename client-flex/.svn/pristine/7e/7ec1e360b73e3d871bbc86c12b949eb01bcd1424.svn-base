package com.stockholdergame.client.ui.events {
    import com.stockholdergame.client.ui.components.classes.GameFilterData;

    import flash.events.Event;

    public class GameFilterEvent extends Event {

        public static const FILTER_APPLIED:String = "filterApplied";

        public function GameFilterEvent(filterData:GameFilterData) {
            super(FILTER_APPLIED, false, false);
            this._filterData = filterData;
        }

        private var _filterData:GameFilterData;

        public function get filterData():GameFilterData {
            return _filterData;
        }

        override public function clone():Event {
            return new GameFilterEvent(_filterData);
        }
    }
}

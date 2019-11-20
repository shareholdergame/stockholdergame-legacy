package com.stockholdergame.client.ui.events {
    import com.stockholdergame.client.model.dto.account.UserFilterDto;

    import flash.events.Event;

    public class UserFilterEvent extends Event {

        public const FILTER_APPLIED:String = "filterApplied";

        public function UserFilterEvent(filter:UserFilterDto) {
            super(FILTER_APPLIED, false, false);
            this._userFilter = filter;
        }

        private var _userFilter:UserFilterDto;

        public function get userFilter():UserFilterDto {
            return _userFilter;
        }

        override public function clone():Event {
            return new UserFilterEvent(_userFilter);
        }
    }
}

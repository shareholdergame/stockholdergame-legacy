package com.stockholdergame.client.model.dto.account {
    import com.stockholdergame.client.model.dto.ProfileDto;

    import flash.utils.ByteArray;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.UserInfoDto")]
    public class UserInfoDto extends UserNameDto implements UserViewDto {
        public function UserInfoDto() {
        }

        private var _locale:String;
        private var _bot:Boolean;
        public var profile:ProfileDto;

        public function get avatar():ByteArray {
            return profile != null ? profile.avatar : null;
        }

        public function get bot():Boolean {
            return _bot;
        }

        public function set bot(value:Boolean):void {
            _bot = value;
        }

        public function get locale():String {
            return _locale;
        }

        public function set locale(value:String):void {
            _locale = value;
        }
    }
}

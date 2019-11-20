package com.stockholdergame.client.model.dto {
    import com.stockholdergame.client.util.StringUtil;

    import flash.utils.ByteArray;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.ProfileDto")]
    public class ProfileDto {

        public function ProfileDto() {
        }

        public static const MALE:String = "M";
        public static const FEMALE:String = "F";

        private var _sex:String;
        public var birthday:Date;
        public var country:String;
        public var region:String;
        public var city:String;
        public var about:String;
        public var avatar:ByteArray;

        public function get sex():String {
            return _sex;
        }

        public function set sex(value:String):void {
            _sex = value == MALE || value == FEMALE ? value : null;
        }

        public var sexKey:String;

        public function get countryCity():String {
            var countryCity:String = "";
            if (!StringUtil.isBlank(city)) {
                countryCity += city;
            }
            if (!StringUtil.isBlank(country)) {
                if (countryCity.length > 0) {
                    countryCity += ", ";
                }
                countryCity += country;
            }
            return countryCity;
        }
    }
}

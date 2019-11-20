package com.stockholdergame.client.model.dto.game.variant {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.variant.GameShareDto")]
    public class GameShareDto {
        public function GameShareDto() {
        }

        public var id:int;
        public var initPrice:int;
        public var initQuantity:int;
        private var _color:String;

        public function get color():String {
            return getColor(_color);
        }

        public function set color(value:String):void {
            _color = value;
        }

        private static function getColor(color:String):String {
            if (color == null) {
                return color;
            } else if (color.toLowerCase() == 'blue') {
                return "#4285F4";
            } else if (color.toLowerCase() == 'red') {
                return "#EA4335";
            } else if (color.toLowerCase() == 'yellow') {
                return "#FBBC05";
            } else if (color.toLowerCase() == 'green') {
                return "#34A853";
            } else {
                return color;
            }
        }

        public function get letter():String {
            return "share.color.letter." + _color.toLowerCase().charAt(0);
        }
    }
}

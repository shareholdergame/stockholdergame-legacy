package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.model.dto.account.UserDto;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.ScoreDto")]
    public class ScoreDto extends ScoreValuesDto {
        public function ScoreDto() {
        }

        public var user:UserDto;
        public var gameVariantName:String;
        public var movesQuantity:int;
        public var moveOrder:int;
    }
}

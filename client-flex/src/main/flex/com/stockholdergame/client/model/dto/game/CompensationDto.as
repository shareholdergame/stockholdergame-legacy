package com.stockholdergame.client.model.dto.game {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.CompensationDto")]
    public class CompensationDto {

        public function CompensationDto() {
        }

        public var id:Number;
        public var sum:int;
    }
}

package com.stockholdergame.client.model.dto.game.lite {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.lite.GamesList")]
    public class GamesList {
        public function GamesList() {
        }

        public var totalCount:int;
        public var games:ArrayCollection;
    }
}

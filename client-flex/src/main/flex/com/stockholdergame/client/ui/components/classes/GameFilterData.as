package com.stockholdergame.client.ui.components.classes {
    import com.stockholdergame.client.model.dto.game.GameFilterDto;

    [Bindable]
    public class GameFilterData {
        public function GameFilterData() {
        }

        public var gameVariantId:Number;
        public var gameVariantName:String;
        public var userName:String;

        public static function mergeFilter(sourceFilterData:GameFilterData, destinationFilterData:GameFilterDto):void {
            if (sourceFilterData == null || destinationFilterData == null) {
                return;
            }
            destinationFilterData.gameVariantId = sourceFilterData.gameVariantId;
            destinationFilterData.userName = sourceFilterData.userName;
        }
    }
}

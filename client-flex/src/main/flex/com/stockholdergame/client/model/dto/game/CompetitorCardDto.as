package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.model.dto.game.variant.CardDto;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.CompetitorCardDto")]
    public class CompetitorCardDto {

        public function CompetitorCardDto() {
        }

        public var id:Number;
        public var cardId:Number;
        public var applied:Boolean;

        // local properties
        public var moveNumber:int;
        public var card:CardDto;

        public function get displayOrder():int {
            return card.displayOrder;
        }
    }
}

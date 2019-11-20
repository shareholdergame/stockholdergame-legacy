package com.stockholdergame.client.model.dto.game.variant {

    import com.stockholdergame.client.model.session.SessionManager;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;
    import mx.utils.ObjectUtil;

    [Bindable]
    public class GameVariantHolder {
        public function GameVariantHolder() {
        }

        public var gameVariant:GameVariantDto;
        public var shareColors:Dictionary = new Dictionary();
        public var shareLetters:Dictionary = new Dictionary();
        public var cards:Dictionary = new Dictionary();
        public var cardOperations:Dictionary = new Dictionary();
        public var gameVariantSummaryList:ArrayCollection = new ArrayCollection();

        public function getShareColor(shareId:Number):String {
            return shareColors[shareId] != null ? shareColors[shareId] : "white";
        }

        public function getShareLetter(shareId:Number):String {
            return shareLetters[shareId] != null ? shareLetters[shareId] : "";
        }

        public function getCard(cardId:Number):CardDto {
            return CardDto(ObjectUtil.copy(cards[cardId]));
        }

        public function getCardOperation(cardId:Number, priceOperationId:Number):CardOperationDto {
            return cardOperations[(cardId + "|" + priceOperationId)];
        }

        public static function getMaxSharePrice(gameVariantId:Number):int {
            return SessionManager.instance.getGameVariantHolder(gameVariantId).gameVariant.priceScale.maxValue;
        }

        public static function getSharePriceStep(gameVariantId:Number):int {
            return SessionManager.instance.getGameVariantHolder(gameVariantId).gameVariant.priceScale.scaleSpacing;
        }
    }
}

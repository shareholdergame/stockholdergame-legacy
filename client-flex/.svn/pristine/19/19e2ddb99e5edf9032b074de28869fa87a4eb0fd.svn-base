package com.stockholdergame.client.model.registry {
    import com.stockholdergame.client.model.dto.game.variant.CardDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantHolder;
    import com.stockholdergame.client.util.sort.SortUtil;
    import com.stockholdergame.client.util.sort.SortUtil;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    public class GameVariantRegistry {
        public function GameVariantRegistry() {
        }

        private var _gameVariants:Dictionary = new Dictionary();

        public function get gameVariants():ArrayCollection {
            var value:ArrayCollection = new ArrayCollection();
            for each (var item:GameVariantHolder in _gameVariants) {
                value.addItem(item.gameVariant);
            }
            return SortUtil.sortByNumericField(value, "movesQuantity");
        }

        public function set gameVariants(value:ArrayCollection):void {
            _gameVariants = new Dictionary();
            for each (var holder:GameVariantHolder in value) {
                _gameVariants[holder.gameVariant.id] = holder;
            }
        }

        public function getGameVariantHolder(gameVariantId:int):GameVariantHolder {
            return _gameVariants[gameVariantId];
        }

        public function getShareColor(gameVariantId:int, shareId:int):String {
            var holder:GameVariantHolder = getGameVariantHolder(gameVariantId);
            return holder.getShareColor(shareId);
        }

        public function getCard(gameVariantId:int, cardId:int):CardDto {
            var holder:GameVariantHolder = getGameVariantHolder(gameVariantId);
            return holder.getCard(cardId);
        }

        public function getCards(gameVariantId:int):ArrayCollection {
            var holder:GameVariantHolder = getGameVariantHolder(gameVariantId);
            var cards:Dictionary = holder.cards;
            var cardsArr:ArrayCollection = new ArrayCollection();
            for each (var cardDto:CardDto in cards) {
                cardsArr.addItem(cardDto);
            }
            return SortUtil.sortByNumericField(cardsArr, "displayOrder");
        }
    }
}

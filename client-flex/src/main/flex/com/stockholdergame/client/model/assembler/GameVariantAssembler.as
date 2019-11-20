package com.stockholdergame.client.model.assembler {
    import com.stockholdergame.client.model.dto.game.variant.CardDto;
    import com.stockholdergame.client.model.dto.game.variant.CardGroupDto;
    import com.stockholdergame.client.model.dto.game.variant.CardOperationDto;
    import com.stockholdergame.client.model.dto.game.variant.GameShareDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantHolder;

    import mx.collections.ArrayCollection;

    public class GameVariantAssembler {

        public static function assemble(gameVariant:GameVariantDto):GameVariantHolder {
            var holder:GameVariantHolder = new GameVariantHolder();
            holder.gameVariant = gameVariant;
            buildShareColors(holder);
            buildCards(holder);
            return holder;
        }

        private static function buildCards(holder:GameVariantHolder):void {
            var cardGroups:ArrayCollection = holder.gameVariant.cardGroups;
            for each (var cardGroupDto:CardGroupDto in cardGroups) {
                var cards:ArrayCollection = cardGroupDto.cards;
                for each (var cardDto:CardDto in cards) {
                    holder.cards[cardDto.id] = cardDto;
                    var cardOperations:ArrayCollection = cardDto.cardOperations;
                    for each (var cardOperationDto:CardOperationDto in cardOperations) {
                        holder.cardOperations[cardDto.id + "|" + cardOperationDto.priceOperationId] = cardOperationDto;
                        cardOperationDto.isShareFixed = cardOperationDto.shareId != 0;
                        cardOperationDto.shareColor = holder.getShareColor(cardOperationDto.shareId);
                        cardOperationDto.colorLetter = holder.getShareLetter(cardOperationDto.shareId);
                    }
                }
            }
        }

        private static function buildShareColors(holder:GameVariantHolder):void {
            var shares:ArrayCollection = holder.gameVariant.shares;
            for each (var gameShareDto:GameShareDto in shares) {
                holder.shareColors[gameShareDto.id] = gameShareDto.color;
                holder.shareLetters[gameShareDto.id] = gameShareDto.letter;
            }
        }
    }
}

package com.stockholdergame.client.model.assembler {
    import com.stockholdergame.client.model.dto.game.CompetitorCardDto;
    import com.stockholdergame.client.model.dto.game.CompetitorDto;
    import com.stockholdergame.client.model.dto.game.CompetitorMoveDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.MoveDto;
    import com.stockholdergame.client.model.dto.game.MoveStepDto;
    import com.stockholdergame.client.model.dto.game.SharePriceDto;
    import com.stockholdergame.client.model.dto.game.ShareQuantityDto;
    import com.stockholdergame.client.model.dto.game.result.CompetitorDiffDto;
    import com.stockholdergame.client.model.dto.game.result.CompetitorResultDto;
    import com.stockholdergame.client.model.dto.game.variant.CardOperationDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantHolder;
    import com.stockholdergame.client.model.dto.game.view.ShareFunds;
    import com.stockholdergame.client.model.session.SessionManager;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    public class GameViewAssembler {

        public function GameViewAssembler() {
        }

        public static function assembleGame(game:GameDto):GameDto {
            var gameVariant:GameVariantHolder = SessionManager.instance.getGameVariantHolder(game.gameVariantId);

            game.movesQuantity = gameVariant.gameVariant.movesQuantity;
            game.gameVariantName = gameVariant.gameVariant.name;

            var competitors:ArrayCollection = game.competitors;

            var moves:ArrayCollection = game.moves;
            var lastMove:MoveDto = MoveDto(moves.getItemAt(moves.length - 1));
            //var prevMove:MoveDto = MoveDto(moves.length > 1 ? moves.getItemAt(moves.length - 2) : null);
            var lastCompetitorMove:CompetitorMoveDto =
                    CompetitorMoveDto(lastMove.competitorMoves.getItemAt(lastMove.competitorMoves.length - 1));
            switchToNextMover(game, lastMove.moveNumber, lastCompetitorMove.moveOrder);

            // create competitors dictionary
            var competitorsMap:Dictionary = new Dictionary();
            var competitorsMapByOrder:Dictionary = new Dictionary();
            var competitorResults:ArrayCollection = game.competitorResults;
            var competitorDiffs:ArrayCollection = game.competitorDiffs;
            var gameSeriesCompetitorResults:ArrayCollection = game.gameSeriesCompetitorResults;
            var gameSeriesCompetitorDiffs:ArrayCollection = game.gameSeriesCompetitorDiffs;
            for each (var competitorDto:CompetitorDto in competitors) {
                competitorsMap[competitorDto.id] = competitorDto;
                competitorDto.competitorMoves = new ArrayCollection();
                competitorDto.currentShareQuantities = new ArrayCollection();
                competitorDto.game = game;
                competitorDto.me = SessionManager.instance.getAccountInfo().userName == competitorDto.userName;
                competitorsMapByOrder[competitorDto.moveOrder] = competitorDto;
                var competitorCards:ArrayCollection = competitorDto.competitorCards;
                for each (var competitorCardDto:CompetitorCardDto in competitorCards) {
                    competitorCardDto.card = gameVariant.getCard(competitorCardDto.cardId);
                }
                for each (var competitorResultDto:CompetitorResultDto in competitorResults) {
                    if (competitorDto.moveOrder == competitorResultDto.moveOrder) {
                        competitorDto.totalPoints = competitorResultDto.totalPoints;
                        competitorDto.winner = competitorResultDto.winner;
                        competitorDto.out = competitorResultDto.out;
                    }
                }
                for each (var competitorDiffDto:CompetitorDiffDto in competitorDiffs) {
                    if (competitorDto.userName == competitorDiffDto.firstUserName) {
                        competitorDto.winSum = competitorDiffDto.fundsAbsoluteDiff;
                    }
                }
                for each (var gameSeriesCompetitorResultDto:CompetitorResultDto in gameSeriesCompetitorResults) {
                    if (competitorDto.userName == gameSeriesCompetitorResultDto.userName) {
                        competitorDto.gameSeriesTotalFunds = gameSeriesCompetitorResultDto.totalFunds;
                        competitorDto.gameSeriesTotalPoints = gameSeriesCompetitorResultDto.totalPoints;
                        competitorDto.gameSeriesWinner = gameSeriesCompetitorResultDto.winner;
                    }
                }
                for each (var gameSeriesCompetitorDiffDto:CompetitorDiffDto in gameSeriesCompetitorDiffs) {
                    if (competitorDto.userName == gameSeriesCompetitorDiffDto.firstUserName) {
                        competitorDto.gameSeriesWinSum = gameSeriesCompetitorDiffDto.fundsAbsoluteDiff;
                    }
                }
            }

            // competitor moves
            var previousCompetitorMove:CompetitorMoveDto = null;
            for each (var moveDto:MoveDto in moves) {
                var competitorMoves:ArrayCollection = moveDto.competitorMoves;
                var competitorCardDto1:CompetitorCardDto = null;
                var lastBuySellStep:MoveStepDto = null;
                for each (var competitorMoveDto:CompetitorMoveDto in competitorMoves) {
                    competitorsMapByOrder[competitorMoveDto.moveOrder].competitorMoves.addItem(competitorMoveDto);
                    if (competitorMoveDto.moveNumber > 0) {
                        competitorCardDto1 = competitorsMapByOrder[competitorMoveDto.moveOrder].getCompetitorCard(competitorMoveDto.appliedCardId);
                        competitorCardDto1.moveNumber = competitorMoveDto.moveNumber;
                        competitorMoveDto.appliedCard = competitorCardDto1;
                        competitorMoveDto.previousCompetitorMove = previousCompetitorMove;
                    }
                    var steps:ArrayCollection = competitorMoveDto.steps;
                    for each (var moveStepDto:MoveStepDto in steps) {
                        if (moveStepDto.sharePrices != null && moveStepDto.sharePrices.length > 0) {
                            assembleSharePrices(moveStepDto.sharePrices, gameVariant, competitorCardDto1);
                            if (moveDto.moveNumber == lastMove.moveNumber
                                    && competitorMoveDto.moveOrder == lastCompetitorMove.moveOrder) {
                                game.currentSharePrices = moveStepDto.sharePrices;
                            }
                        }
                        if (moveStepDto.shareQuantities != null && moveStepDto.shareQuantities.length > 0) {
                            assembleShareQuantities(moveStepDto, lastBuySellStep);
                            competitorsMapByOrder[competitorMoveDto.moveOrder].currentShareQuantities =
                                    moveStepDto.shareQuantities;
                        }

                        competitorsMapByOrder[competitorMoveDto.moveOrder].currentCash = moveStepDto.cashValue;
                        if (moveStepDto.stepType == MoveStepDto.LAST_BUY_SELL_STEP
                                || moveStepDto.stepType == MoveStepDto.REPURCHASE_STEP
                                || (moveStepDto.stepType == MoveStepDto.PRICE_CHANGE_STEP && moveDto.moveNumber == game.movesQuantity)) {
                            lastBuySellStep = moveStepDto;
                        }
                    }
                    previousCompetitorMove = competitorMoveDto;
                }
            }

            for each (var competitorDto1:CompetitorDto in competitors) {
                assembleFunds(game, competitorDto1);
            }

            return game;
        }

        private static function switchToNextMover(game:GameDto, lastMoveNumber:int, lastMoveOrder:int):void {
            if (lastMoveOrder == game.competitors.length) {
                if (lastMoveNumber == game.movesQuantity) {
                    return;
                }
                lastMoveNumber++;
                lastMoveOrder = 1;
            } else {
                lastMoveOrder++;
            }
            var nextCompetitor:CompetitorDto;
            var competitors:ArrayCollection = game.competitors;
            for each (var competitorDto:CompetitorDto in competitors) {
                if (competitorDto.moveOrder == lastMoveOrder) {
                    nextCompetitor = competitorDto;
                }
            }
            if (nextCompetitor.out) {
                switchToNextMover(game, lastMoveNumber, lastMoveOrder);
            } else if (lastMoveOrder == game.currentCompetitorNumber) {
                // game finished
            } else {
                game.currentMoveNumber = lastMoveNumber;
                game.currentCompetitorNumber = lastMoveOrder;
            }
        }

        private static function assembleShareQuantities(currStep:MoveStepDto, prevStep:MoveStepDto):void {
            for each (var shareQuantityDto:ShareQuantityDto in currStep.shareQuantities) {
                shareQuantityDto.oldQuantity = shareQuantityDto.quantity;
                if (currStep.stepType == MoveStepDto.REPURCHASE_STEP && prevStep != null) {
                    for each (var shareQuantityDto1:ShareQuantityDto in prevStep.shareQuantities) {
                        if (shareQuantityDto1.id == shareQuantityDto.id) {
                            shareQuantityDto.prevStepQuantity = shareQuantityDto1.quantity;
                        }
                    }
                }
            }
        }

        private static function assembleFunds(game:GameDto, competitorDto:CompetitorDto):void {
            if (competitorDto.totalFunds != 0) {
                return;
            }
            var totalFunds:int = competitorDto.currentCash;
            var shareFundsList:ArrayCollection = new ArrayCollection();
            for each (var shareQuantityDto:ShareQuantityDto in competitorDto.currentShareQuantities) {
                var price:int = game.getCurrentSharePrice(shareQuantityDto.id).price;
                var shareFunds:ShareFunds = new ShareFunds();
                shareFunds.id = shareQuantityDto.id;
                shareFunds.sum = price * shareQuantityDto.quantity;
                shareFundsList.addItem(shareFunds);
                totalFunds += shareFunds.sum;
            }
            competitorDto.shareFunds = shareFundsList;
            competitorDto.totalFunds = totalFunds;
        }

        private static function assembleSharePrices(sharePrices:ArrayCollection, gameVariant:GameVariantHolder,
                                             competitorCardDto1:CompetitorCardDto):ArrayCollection {
            for each (var sharePriceDto:SharePriceDto in sharePrices) {
                sharePriceDto.color = gameVariant.getShareColor(sharePriceDto.id);
                sharePriceDto.letter = gameVariant.getShareLetter(sharePriceDto.id);
                if (sharePriceDto.priceOperationId != 0) {
                    var cardOperation:CardOperationDto = gameVariant.getCardOperation(competitorCardDto1.card.id, sharePriceDto.priceOperationId);
                    sharePriceDto.operation = cardOperation.operation;
                    sharePriceDto.operandValue = cardOperation.operandValue;
                    sharePriceDto.oldPrice = sharePriceDto.price;
                    sharePriceDto.mainColor = cardOperation.isShareFixed;
                    var cardOperations:ArrayCollection = competitorCardDto1.card.cardOperations;
                    for each (var cardOperationDto:CardOperationDto in cardOperations) {
                        if (!cardOperationDto.isShareFixed
                                && sharePriceDto.priceOperationId == cardOperationDto.priceOperationId) {
                            cardOperationDto.shareId = sharePriceDto.id;
                            cardOperationDto.shareColor = sharePriceDto.color;
                            cardOperationDto.colorLetter = sharePriceDto.letter;
                        }
                    }
                }
            }
            return sharePrices;
        }
    }
}

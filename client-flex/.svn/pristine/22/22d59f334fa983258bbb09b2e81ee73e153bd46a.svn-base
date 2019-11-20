package com.stockholdergame.client.model.dto.game {

    import com.stockholdergame.client.model.dto.account.UserViewDto;
    import com.stockholdergame.client.util.sort.SortUtil;

    import flash.utils.ByteArray;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.CompetitorDto")]
    public class CompetitorDto implements UserViewDto {

        public function CompetitorDto() {
        }

        public var id:Number;
        public var userName:String;
        public var avatar:ByteArray;
        public var bot:Boolean;
        public var locale:String;
        public var moveOrder:int;
        public var joinedTime:Date;
        public var initiator:Boolean;
        public var out:Boolean;

        public var totalFunds:int;
        public var totalPoints:Number;
        public var winner:Boolean;
        public var winSum:int;
        public var gameSeriesTotalFunds:int;
        public var gameSeriesTotalPoints:Number;
        public var gameSeriesWinner:Boolean;
        public var gameSeriesWinSum:int;

        private var _competitorCards:Dictionary;

        public function get competitorCards():ArrayCollection {
            var availableCards:ArrayCollection = new ArrayCollection();
            var appliedCards:ArrayCollection = new ArrayCollection();
            for each (var competitorCardDto:CompetitorCardDto in _competitorCards) {
                if (competitorCardDto.applied) {
                    appliedCards.addItem(competitorCardDto);
                } else {
                    availableCards.addItem(competitorCardDto);
                }
            }
            appliedCards = SortUtil.sortByNumericField(appliedCards, "moveNumber");
            availableCards = SortUtil.sortByNumericField(availableCards, "cardId");
            var allCards:ArrayCollection = new ArrayCollection();
            allCards.addAll(availableCards);
            allCards.addAll(appliedCards);
            return allCards;
        }

        public function set competitorCards(value:ArrayCollection):void {
            _competitorCards = new Dictionary();
            for each (var competitorCardDto:CompetitorCardDto in value) {
                _competitorCards[competitorCardDto.id] = competitorCardDto;
            }
        }

        public function getCompetitorCard(id:Number):CompetitorCardDto {
            return _competitorCards != null ? _competitorCards[id] : null;
        }

        // local properties
        public var game:GameDto;

        public var me:Boolean;
        private var _competitorMoves:ArrayCollection;
        private var _currentShareQuantities:ArrayCollection;
        public var currentCash:int;
        public var currentCompensationSum:int = 0;
        private var _shareFunds:ArrayCollection;

        public function get competitorMoves():ArrayCollection {
            return _competitorMoves;
        }

        public function set competitorMoves(value:ArrayCollection):void {
            _competitorMoves = SortUtil.sortByNumericField(value, "moveNumber");
        }

        public function get currentShareQuantities():ArrayCollection {
            return _currentShareQuantities;
        }

        public function set currentShareQuantities(value:ArrayCollection):void {
            _currentShareQuantities = SortUtil.sortByNumericField(value, "id");
        }

        public function get shareFunds():ArrayCollection {
            return _shareFunds;
        }

        public function set shareFunds(value:ArrayCollection):void {
            _shareFunds = SortUtil.sortByNumericField(value, "id");
        }

        public function get competitorCardsOrderByDisplayOrder():ArrayCollection {
            var cards:ArrayCollection = new ArrayCollection();
            for each (var competitorCardDto:CompetitorCardDto in _competitorCards) {
                cards.addItem(competitorCardDto);
            }
            return SortUtil.sortByNumericField(cards, "displayOrder");
        }
    }
}

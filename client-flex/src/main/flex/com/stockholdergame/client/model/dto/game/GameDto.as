package com.stockholdergame.client.model.dto.game {

    import com.stockholdergame.client.util.collection.CollectionUtil;
    import com.stockholdergame.client.util.sort.SortUtil;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.GameDto")]
    public class GameDto {

        public function GameDto() {
        }

        public static const OPEN:String = "OPEN";
        public static const RUNNING:String = "RUNNING";
        public static const FINISHED:String = "FINISHED";

        public static const ROUND_DOWN:String = "D";
        public static const ROUND_UP:String = "U";

        public static const GAME_OFFER:String = "GAME_OFFER";
        public static const INVITATION:String = "INVITATION";

        public static const RULES_1_0:String  = "1.0";
        public static const RULES_1_3:String  = "1.3";
        public static const LEGACY_RULES:String = RULES_1_0;

        public var id:Number;
        public var gameVariantId:Number;
        public var rounding:String;
        public var competitorsQuantity:int;
        public var gameStatus:String;
        public var createdTime:Date;
        public var startedTime:Date;
        public var finishedTime:Date;
        public var gameLetter:String;
        public var gameSeriesId:Number;
        public var switchMoveOrder:Boolean;
        private var _relatedGames:ArrayCollection;
        public var competitorResults:ArrayCollection;
        public var competitorDiffs:ArrayCollection;
        public var gameSeriesCompetitorResults:ArrayCollection;
        public var gameSeriesCompetitorDiffs:ArrayCollection;
        public var label:String;
        public var rulesVersion:String;
        private var _moves:ArrayCollection;
        private var _competitors:ArrayCollection;

        public function get relatedGames():ArrayCollection {
            return _relatedGames;
        }

        public function set relatedGames(value:ArrayCollection):void {
            _relatedGames = SortUtil.sortByStringField(value, "gameLetter");
        }

        public function get competitors():ArrayCollection {
            return _competitors;
        }

        public function set competitors(value:ArrayCollection):void {
            _competitors = SortUtil.sortByNumericField(value, "moveOrder");
        }

        public function get moves():ArrayCollection {
            return _moves;
        }

        public function get movesWithoutZero():ArrayCollection {
            return CollectionUtil.subList(_moves, 1);
        }

        public function set moves(value:ArrayCollection):void {
            _moves = SortUtil.sortByNumericField(value, "moveNumber");
        }

        // local properties
        public var currentMoveNumber:int;
        public var currentCompetitorNumber:int;
        public var movesQuantity:int;
        public var gameVariantName:String;
        private var _currentSharePrices:Dictionary;

        public function getCurrentSharePrice(id:Number):SharePriceDto {
            return _currentSharePrices[id];
        }

        public function get currentSharePrices():ArrayCollection {
            var values:ArrayCollection = new ArrayCollection();
            for each (var sharePriceDto:SharePriceDto in _currentSharePrices) {
                values.addItem(sharePriceDto);
            }
            return SortUtil.sortByNumericField(values, "id");
        }

        public function set currentSharePrices(value:ArrayCollection):void {
            _currentSharePrices = new Dictionary();
            for each (var sharePriceDto:SharePriceDto in value) {
                _currentSharePrices[sharePriceDto.id] = sharePriceDto;
            }
        }

        public function get me():CompetitorDto {
            var competitors:ArrayCollection = _competitors;
            for each (var competitorDto:CompetitorDto in competitors) {
                if (competitorDto.me) {
                    return competitorDto;
                }
            }
            return null;
        }

        public function get meOut():Boolean {
            return me.out;
        }

        public function get finished():Boolean {
            return gameStatus == GameDto.FINISHED;
        }
    }
}

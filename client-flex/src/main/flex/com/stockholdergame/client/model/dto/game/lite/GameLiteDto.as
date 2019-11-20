package com.stockholdergame.client.model.dto.game.lite {

    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.lite.GameLite")]
    public class GameLiteDto {
        public function GameLiteDto() {
        }

        public var id:Number;
        public var gameVariantId:int;
        public var gameVariantName:String;
        public var movesQuantity:int;
        public var competitorsQuantity:int;
        public var rounding:String;
        public var gameStatus:String;
        public var createdTime:Date;
        public var startedTime:Date;
        public var finishedTime:Date;
        public var initiationMethod:String;
        public var gameLetter:String;
        public var label:String;
        public var rulesVersion:String;
        public var switchMoveOrder:Boolean;

        public var lastMoveNumber:int;
        public var lastMoveOrder:int;
        public var lastMoveTime:Date;

        private var _competitors:ArrayCollection;
        private var _shares:ArrayCollection;

        public var currentUserInitiator:Boolean = false;
        public var currentUserJoined:Boolean = false;

        public function get competitors():ArrayCollection {
            var items:ArrayCollection = new ArrayCollection();
            if (gameStatus == GameDto.OPEN) {
                var initiatorUser:CompetitorLiteDto;
                var joinedUsers:ArrayCollection = new ArrayCollection();
                for each (var competitorLiteDto:CompetitorLiteDto in _competitors) {
                    if (competitorLiteDto.initiator) {
                        initiatorUser = competitorLiteDto;
                    } else {
                        joinedUsers.addItem(competitorLiteDto);
                    }
                }
                items.addItem(initiatorUser);
                items.addAll(SortUtil.sortByStringField(joinedUsers, "userName"));
            } else {
                if (_competitors != null && _competitors.length > 0) {
                    if (CompetitorLiteDto(_competitors.getItemAt(0)).moveOrder == 0) {
                        items = SortUtil.sortByStringField(_competitors, "userName");
                    } else {
                        items = SortUtil.sortByNumericField(_competitors, "moveOrder");
                    }
                }
            }

            return items;
        }

        public function set competitors(value:ArrayCollection):void {
            _competitors = value;
        }

        public function get initiator():CompetitorLiteDto {
            for each (var competitorLiteDto:CompetitorLiteDto in _competitors) {
                if (competitorLiteDto.initiator) {
                    return competitorLiteDto;
                }
            }
            return null;
        }

        public function get currentMoveMaker():CompetitorLiteDto {
            for each (var competitorLiteDto:CompetitorLiteDto in _competitors) {
                if (competitorLiteDto.currentMove) {
                    return competitorLiteDto;
                }
            }
            return null;
        }

        public function get otherCompetitors():ArrayCollection {
            var other:ArrayCollection = new ArrayCollection();
            for each (var competitorLiteDto:CompetitorLiteDto in _competitors) {
                if (!competitorLiteDto.currentMove) {
                    other.addItem(competitorLiteDto);
                }
            }
            return other;
        }

        public function get invitedUsers():ArrayCollection {
            var invitedUsers:ArrayCollection = new ArrayCollection();
            for each (var competitorLiteDto:CompetitorLiteDto in _competitors) {
                if (competitorLiteDto.invitation && !competitorLiteDto.initiator && !competitorLiteDto.me) {
                    invitedUsers.addItem(competitorLiteDto);
                }
            }
            return SortUtil.sortByStringField(invitedUsers, "userName");
        }

        public function get shares():ArrayCollection {
            return _shares;
        }

        public function set shares(value:ArrayCollection):void {
            if (value != null) {
                _shares = SortUtil.sortByNumericField(value, "id");
            }
        }
    }
}

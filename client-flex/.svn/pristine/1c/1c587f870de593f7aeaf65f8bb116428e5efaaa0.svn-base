package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.MoveDto")]
    public class MoveDto {
        public function MoveDto() {
        }

        public var moveNumber:int;
        private var _competitorMoves:ArrayCollection;

        public function get competitorMoves():ArrayCollection {
            return _competitorMoves;
        }

        public function set competitorMoves(value:ArrayCollection):void {
            _competitorMoves = SortUtil.sortByNumericField(value, "moveOrder");
        }

        public function competitorMove(moveOrder:int):CompetitorMoveDto {
            for each (var competitorMoveDto:CompetitorMoveDto in competitorMoves) {
                if (competitorMoveDto.moveOrder == moveOrder) {
                    return competitorMoveDto;
                }
            }
            return null;
        }
    }
}

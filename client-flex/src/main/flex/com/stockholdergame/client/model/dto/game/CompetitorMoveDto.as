package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.CompetitorMoveDto")]
    public class CompetitorMoveDto {

        public function CompetitorMoveDto() {
        }

        public var moveNumber:int;
        public var moveOrder:int;
        public var appliedCardId:int;
        public var finishedTime:Date;
        private var _steps:ArrayCollection;

        public function get steps():ArrayCollection {
            return _steps;
        }

        public function set steps(value:ArrayCollection):void {
            _steps = new ArrayCollection();
            var actionSteps:ArrayCollection = new ArrayCollection();
            var compensationAndRepurchaseSteps:ArrayCollection = new ArrayCollection();
            var bankruptStep:ArrayCollection = new ArrayCollection();
            for each (var moveStepDto:MoveStepDto in value) {
                if (moveStepDto.stepNum < 4) {
                    actionSteps.addItem(moveStepDto);
                } else if (moveStepDto.stepNum == 4 || moveStepDto.stepNum == 6) {
                    compensationAndRepurchaseSteps.addItem(moveStepDto);
                } else if (moveStepDto.stepNum == 5) {
                    bankruptStep.addItem(moveStepDto);
                }
            }
            _steps.addAll(SortUtil.sortByNumericField(actionSteps, "stepNum"));
            _steps.addAll(SortUtil.sortByNumericFields(compensationAndRepurchaseSteps, ["originalStepId", "stepNum"]));
            _steps.addAll(bankruptStep);
        }

        // local properties
        public var compensationSum:int = 0;
        public var appliedCard:CompetitorCardDto;
        public var previousCompetitorMove:CompetitorMoveDto;

        public function getStepByType(stepType:String):ArrayCollection {
            var steps:ArrayCollection = new ArrayCollection();
            for each (var moveStep:MoveStepDto in _steps) {
                if (moveStep.stepType == stepType) {
                    steps.addItem(moveStep);
                }
            }
            return stepType == MoveStepDto.COMPENSATION_STEP || stepType == MoveStepDto.REPURCHASE_STEP
                    ? SortUtil.sortByNumericField(steps, "originalStepId") : steps;
        }
    }
}

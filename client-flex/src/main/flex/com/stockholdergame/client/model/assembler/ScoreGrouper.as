package com.stockholdergame.client.model.assembler {
    import com.stockholdergame.client.model.dto.game.ScoreDto;
    import com.stockholdergame.client.model.dto.game.score.GroupedByGameVariantScoreDto;
    import com.stockholdergame.client.model.dto.game.score.GroupedByMoveOrderScoreDto;
    import com.stockholdergame.client.model.dto.game.score.GroupedByUserNameScoreDto;
    import com.stockholdergame.client.util.sort.SortUtil;

    import mx.collections.ArrayCollection;

    public class ScoreGrouper {

        public static function group(scoreDtos:ArrayCollection):ArrayCollection {
            var sortedScoreDtos:ArrayCollection = SortUtil.sort(scoreDtos, compareTo);
            var resultCollection:ArrayCollection = new ArrayCollection();

            var currGroupedByUserNameScoreDto:GroupedByUserNameScoreDto;
            var currGroupedByGameVariantScoreDto:GroupedByGameVariantScoreDto;
            var currGroupedByMoveOrderScoreDto:GroupedByMoveOrderScoreDto;

            for each (var scoreDto:ScoreDto in sortedScoreDtos) {
                if (currGroupedByUserNameScoreDto == null || currGroupedByUserNameScoreDto.user.userName != scoreDto.user.userName) {
                    currGroupedByUserNameScoreDto = new GroupedByUserNameScoreDto();
                    currGroupedByUserNameScoreDto.user = scoreDto.user;
                    currGroupedByGameVariantScoreDto = null;
                    currGroupedByMoveOrderScoreDto = null;
                    resultCollection.addItem(currGroupedByUserNameScoreDto);
                }
                if (currGroupedByGameVariantScoreDto == null || currGroupedByGameVariantScoreDto.gameVariantName != scoreDto.gameVariantName
                        || currGroupedByGameVariantScoreDto.movesQuantity != scoreDto.movesQuantity) {
                    currGroupedByGameVariantScoreDto = new GroupedByGameVariantScoreDto();
                    currGroupedByGameVariantScoreDto.gameVariantName = scoreDto.gameVariantName;
                    currGroupedByGameVariantScoreDto.movesQuantity = scoreDto.movesQuantity;
                    currGroupedByMoveOrderScoreDto = null;
                    currGroupedByUserNameScoreDto.scoreGroupedByGameVariant.addItem(currGroupedByGameVariantScoreDto);
                }
                if (currGroupedByMoveOrderScoreDto == null || currGroupedByMoveOrderScoreDto.moveOrder != scoreDto.moveOrder) {
                    currGroupedByMoveOrderScoreDto = new GroupedByMoveOrderScoreDto();
                    currGroupedByMoveOrderScoreDto.moveOrder = scoreDto.moveOrder;
                    currGroupedByMoveOrderScoreDto.winningsCount = scoreDto.winningsCount;
                    currGroupedByMoveOrderScoreDto.defeatsCount = scoreDto.defeatsCount;
                    currGroupedByMoveOrderScoreDto.bankruptsCount = scoreDto.bankruptsCount;
                    currGroupedByMoveOrderScoreDto.totalPlayed = scoreDto.winningsCount + scoreDto.defeatsCount;
                    currGroupedByGameVariantScoreDto.scoreGroupedByMoveOrder.addItem(currGroupedByMoveOrderScoreDto);
                }

                currGroupedByGameVariantScoreDto.totalWinningsCount += scoreDto.winningsCount;
                currGroupedByGameVariantScoreDto.totalDefeatsCount += scoreDto.defeatsCount;
                currGroupedByGameVariantScoreDto.totalBankruptsCount += scoreDto.bankruptsCount;
                currGroupedByGameVariantScoreDto.totalPlayed += (scoreDto.winningsCount + scoreDto.defeatsCount);

                currGroupedByUserNameScoreDto.totalWinningsCount += scoreDto.winningsCount;
                currGroupedByUserNameScoreDto.totalDefeatsCount += scoreDto.defeatsCount;
                currGroupedByUserNameScoreDto.totalBankruptsCount += scoreDto.bankruptsCount;
                currGroupedByUserNameScoreDto.totalPlayed += (scoreDto.winningsCount + scoreDto.defeatsCount);
            }

            return resultCollection;
        }

        private static function compareTo(a:ScoreDto, b:ScoreDto):int {
            var result:int = 0;

            result = a.user.userName.localeCompare(b.user.userName);

            if (result == 0) {
                result = a.gameVariantName.localeCompare(b.gameVariantName) + (a.movesQuantity - b.movesQuantity);
            }

            if (result == 0) {
                result = a.moveOrder - b.moveOrder;
            }

            return result;
        }
    }
}

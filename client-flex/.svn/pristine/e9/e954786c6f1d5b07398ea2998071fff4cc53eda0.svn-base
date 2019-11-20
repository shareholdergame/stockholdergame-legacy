package com.stockholdergame.client.model.registry {
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.util.sort.SortUtil;

    import flash.utils.Dictionary;

    import mx.collections.ArrayCollection;

    public class PlayedGamesRegistry {
        public function PlayedGamesRegistry() {
        }

        private var _loadedGames:Dictionary = new Dictionary();

        public function get loadedGames():ArrayCollection {
            var value:ArrayCollection = new ArrayCollection();
            for each (var game:GameDto in _loadedGames) {
                value.addItem(game);
            }
            return SortUtil.sortByNumericField(value, "id");
        }

        public function addLoadedGame(game:GameDto):void {
            _loadedGames[game.id] = game;
        }

        public function isGameLoaded(id:Number):Boolean {
            return _loadedGames[id] != null;
        }

        public function unloadGame(gameId:Number):void {
            _loadedGames[gameId] = null;
        }

        public function getGame(gameId:Number):GameDto {
            return _loadedGames[gameId];
        }
    }
}

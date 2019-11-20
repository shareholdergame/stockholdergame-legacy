package com.stockholdergame.client.model.session {
    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantHolder;
    import com.stockholdergame.client.model.registry.GameVariantRegistry;
    import com.stockholdergame.client.model.registry.PlayedGamesRegistry;

    import mx.collections.ArrayCollection;

    public class SessionManager {

        private static var _instance:SessionManager;

        function SessionManager() {
        }

        public static function get instance():SessionManager {
            if (_instance == null) {
                _instance = new SessionManager();
            }
            return _instance;
        }

        private var session:Session = new Session();

        public function setAccountInfo(myAccountDto:MyAccountDto):void {
            session.setAttribute(myAccountDto, SessionAttribute.MY_ACCOUNT_INFO);
        }

        public function isRemoved():Boolean {
            return MyAccountDto(session.getAttribute(SessionAttribute.MY_ACCOUNT_INFO)).status == MyAccountDto.ACCOUNT_STATUS_REMOVED;
        }

        public function getAccountInfo():MyAccountDto {
            return MyAccountDto(session.getAttribute(SessionAttribute.MY_ACCOUNT_INFO));
        }

        public function getGameVariants():ArrayCollection {
            return session.isAttributeNotNull(SessionAttribute.GAME_VARIANTS)
                    ? GameVariantRegistry(session.getAttribute(SessionAttribute.GAME_VARIANTS)).gameVariants : new ArrayCollection();
        }

        public function getShareColor(gameVariantId:Number, shareId:Number):String {
            return GameVariantRegistry(session.getAttribute(SessionAttribute.GAME_VARIANTS)).getShareColor(gameVariantId, shareId);
        }

        public function getCards(gameVariantId:Number):ArrayCollection {
            return GameVariantRegistry(session.getAttribute(SessionAttribute.GAME_VARIANTS)).getCards(gameVariantId);
        }

        public function getGameVariantHolder(gameVariantId:Number):GameVariantHolder {
            return GameVariantRegistry(session.getAttribute(SessionAttribute.GAME_VARIANTS)).getGameVariantHolder(gameVariantId);
        }

        public function isGameLoaded(gameId:Number):Boolean {
            return session.isAttributeNotNull(SessionAttribute.LOADED_GAMES)
                    && PlayedGamesRegistry(session.getAttribute(SessionAttribute.LOADED_GAMES)).isGameLoaded(gameId);
        }

        public function getGame(gameId:Number):GameDto {
            return PlayedGamesRegistry(session.getAttribute(SessionAttribute.LOADED_GAMES)).getGame(gameId);
        }

        public function unloadGame(gameId:Number):void {
            PlayedGamesRegistry(session.getAttribute(SessionAttribute.LOADED_GAMES)).unloadGame(gameId);
        }

        public function saveGameInSession(game:GameDto):void {
            if (session.getAttribute(SessionAttribute.LOADED_GAMES) == null) {
                session.setAttribute(new PlayedGamesRegistry(), SessionAttribute.LOADED_GAMES);
            }
            PlayedGamesRegistry(session.getAttribute(SessionAttribute.LOADED_GAMES)).addLoadedGame(game);
        }

        public function setGameVariants(gameVariants:ArrayCollection):void {
            var gameVariantRegistry:GameVariantRegistry = new GameVariantRegistry();
            gameVariantRegistry.gameVariants = gameVariants;
            session.setAttribute(gameVariantRegistry, SessionAttribute.GAME_VARIANTS);
        }

        public function clearSession():void {
            session.clear();
        }

        public function setFriends(friendsList:ArrayCollection):void {
            session.setAttribute(friendsList, SessionAttribute.FRIENDS);
        }

        public function getFriends():ArrayCollection {
            var list:ArrayCollection = ArrayCollection(session.getAttribute(SessionAttribute.FRIENDS));
            return list != null ? list : new ArrayCollection();
        }
    }
}

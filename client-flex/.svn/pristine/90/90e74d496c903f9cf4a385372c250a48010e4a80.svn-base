package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.configuration.ApplicationConfiguration;
    import com.stockholdergame.client.model.assembler.GameVariantAssembler;
    import com.stockholdergame.client.model.dto.account.MyAccountDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.MessageResources;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.message.Message;
    import com.stockholdergame.client.ui.message.MessageSeverity;

    import mx.collections.ArrayCollection;

    public class InitializeUserSessionCommand extends ProxyAwareCommand {

        public function InitializeUserSessionCommand() {
            registerNotificationHandler(BusinessActions.INIT_USER_SESSION, initUserSession);
        }

        private function initUserSession():void {
            accountServiceProxy.getAccountInfo(handleGetAccountInfo);
        }

        private function handleGetAccountInfo(result:Object):void {
            var myAccountDto:MyAccountDto = result as MyAccountDto;
            if (ApplicationConfiguration.instance.activeEnvironment.messagingEnabled
                    && myAccountDto.status != MyAccountDto.ACCOUNT_STATUS_REMOVED) {
                messagingServiceProxy.subscribeOnEvents(myAccountDto.subtopicName);
            }
            if (myAccountDto.accountOperations != null && myAccountDto.accountOperations.length > 0) {
                sendNotification(BusinessActions.SHOW_CONFIRMATION_PAGE, myAccountDto.accountOperations);
            }
            gameServiceProxy.getGameVariants(getGameVariantsCallback);
        }

        private function getGameVariantsCallback(gameVariants:ArrayCollection):void {
            if (gameVariants.length == 0) {
                sendNotification(Notifications.SHOW_POPUP, new Message(MessageResources.GAME_VARIANTS_EMPTY, MessageSeverity.ERROR));
            } else {
                var holders:ArrayCollection = new ArrayCollection();
                for each (var gameVariant:GameVariantDto in gameVariants) {
                    holders.addItem(GameVariantAssembler.assemble(gameVariant));
                }
                sessionManager.setGameVariants(holders);
            }
            sendNotification(Notifications.SESSION_INITIALIZED);
        }
    }
}

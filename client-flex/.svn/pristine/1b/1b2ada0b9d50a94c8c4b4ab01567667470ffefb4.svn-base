package com.stockholdergame.client.mvc.mediator {
    import com.stockholdergame.client.model.dto.game.CompetitorCardDto;
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;
    import com.stockholdergame.client.model.dto.game.lite.GamesList;
    import com.stockholdergame.client.model.dto.game.variant.CardDto;
    import com.stockholdergame.client.model.dto.game.variant.GameVariantDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.components.dialog.CardSetDialog;
    import com.stockholdergame.client.ui.components.list.listClasses.GameVariantListItemData;
    import com.stockholdergame.client.ui.components.page.NewGamePage;
    import com.stockholdergame.client.ui.components.page.SearchUserPage;
    import com.stockholdergame.client.ui.components.page.pageClasses.SearchUserPageData;
    import com.stockholdergame.client.ui.events.BusinessActions;
    import com.stockholdergame.client.ui.events.BusinessEvent;

    import mx.collections.ArrayCollection;
    import mx.managers.PopUpManager;

    public class NewGamePageMediator extends ProxyAwareMediator {

        public static const NAME:String = "com.stockholdergame.client.mvc.mediator.NewGamePageMediator";

        public function NewGamePageMediator(viewComponent:NewGamePage) {
            super(NAME, viewComponent);
            registerNotificationHandler(Notifications.GAME_INITIATED, handleGameInitiated);
            registerNotificationHandler(Notifications.GAME_VARIANTS_LOADED, handleGameVariantsLoaded);
            registerNotificationHandler(Notifications.OPEN_GAMES_LOADED, handleOpenGamesLoaded);
            registerNotificationHandler(Notifications.INVITATION_SENT, handleInvitationSent);
            registerNotificationHandler(Notifications.INVITATION_STATUS_CHANGED, handleInvitationStatusChanged);
            registerNotificationHandler(Notifications.ACCOUNT_STATUS_CHANGED, handleAccountStatusChanged);
            registerNotificationHandler(Notifications.REFRESH_GAME_LISTS, handleRefreshGameLists);
        }

        override public function onRegister():void {
            registerAction(BusinessActions.INITIATE_GAME);
            registerAction(BusinessActions.CANCEL_GAME);
            registerAction(BusinessActions.GET_OPEN_GAMES);
            registerAction(BusinessActions.JOIN_TO_GAME);
            registerAction(BusinessActions.CHANGE_INVITATION_STATUS);
            registerAction(BusinessActions.GET_GAME_VARIANT_SUMMARY);
            addEventListener(BusinessActions.SHOW_CARD_SET_DIALOG, onShowCardSetDialog);
            addEventListener(BusinessActions.SHOW_INVITE_USER_DIALOG, onShowInviteUserDialog);
        }

        private function onShowInviteUserDialog(event:BusinessEvent):void {
            sendNotification(Notifications.SHOW_SEARCH_USER_PAGE, new SearchUserPageData(SearchUserPage.INVITE_STATE, GameLiteDto(event.data)));
        }

        private function handleRefreshGameLists():void {
            gameVariantSelectionPage.refreshData();
        }

        private function onShowCardSetDialog(event:BusinessEvent):void {
            var gameVariantId:Number = Number(event.data);
            var cardSetDialog:CardSetDialog;
            var cards:ArrayCollection = sessionManager.getCards(gameVariantId);
            var cardGroups:ArrayCollection = new ArrayCollection();
            for each (var cardDto:CardDto in cards) {
                if (!containsGroup(cardGroups, cardDto.groupName)) {
                    cardGroups.addItem({groupName: cardDto.groupName, cards: new ArrayCollection()});
                }
                var cardGroup:Object = getCardGroupByName(cardGroups, cardDto.groupName);

                var ccd:CompetitorCardDto = new CompetitorCardDto();
                ccd.card = cardDto;
                for (var i:int = 0; i < cardDto.quantity; i++) {
                    cardGroup.cards.addItem(ccd);
                }
            }
            cardSetDialog = CardSetDialog(PopUpManager.createPopUp(gameVariantSelectionPage, CardSetDialog, true));
            cardSetDialog.cardGroups = cardGroups;
            PopUpManager.centerPopUp(cardSetDialog);
            PopUpManager.bringToFront(cardSetDialog);
        }

        private static function getCardGroupByName(cardGroups:ArrayCollection, groupName:String):Object {
            for each (var item:Object in cardGroups) {
                if (item.groupName == groupName) {
                    return item;
                }
            }
            return null;
        }

        private static function containsGroup(cardGroups:ArrayCollection, groupName:String):Boolean {
            for each (var item:Object in cardGroups) {
                if (item.groupName == groupName) {
                    return true;
                }
            }
            return false;
        }

        private function handleGameVariantsLoaded():void {
            var gameVariantsList:ArrayCollection = sessionManager.getGameVariants();
            if(gameVariantsList.length > 0) {
                var gvlist:ArrayCollection = new ArrayCollection();
                for each (var gameVariant:GameVariantDto in gameVariantsList) {
                    var gameVariantSummaryList:ArrayCollection =
                            sessionManager.getGameVariantHolder(gameVariant.id).gameVariantSummaryList;
                    gvlist.addItem(new GameVariantListItemData(gameVariant, gameVariantSummaryList));
                }
                gameVariantSelectionPage.gameVariantsList = gvlist;
                gameVariantSelectionPage.gameVariantCommonData = GameVariantListItemData(gvlist.getItemAt(0)).gameVariant;
            }
        }

        private function handleOpenGamesLoaded(gamesList:GamesList):void {
            gameVariantSelectionPage.openGames = gamesList.games;
        }

        private function handleGameInitiated():void {
            gameVariantSelectionPage.refreshData();
        }

        private function handleAccountStatusChanged(status:String):void {
            gameVariantSelectionPage.validateNow();
        }

        private function get gameVariantSelectionPage():NewGamePage {
            return viewComponent as NewGamePage;
        }

        private function handleInvitationSent():void {
            gameVariantSelectionPage.refreshData();
        }

        private function handleInvitationStatusChanged():void {
            gameVariantSelectionPage.refreshData();
        }
    }
}

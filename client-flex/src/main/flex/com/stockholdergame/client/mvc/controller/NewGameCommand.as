package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.game.CreateInvitationDto;
    import com.stockholdergame.client.model.dto.game.EditInvitationsDto;
    import com.stockholdergame.client.model.dto.game.GameDto;
    import com.stockholdergame.client.model.dto.game.GameStatusDto;
    import com.stockholdergame.client.model.dto.game.GameInitiationDto;
    import com.stockholdergame.client.model.dto.game.ChangeInvitationStatusDto;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.ui.events.BusinessActions;

    public class NewGameCommand extends ProxyAwareCommand {
        public function NewGameCommand() {
            registerNotificationHandler(BusinessActions.INITIATE_GAME, handleInitiateGame);
            registerNotificationHandler(BusinessActions.CANCEL_GAME, handleCancelGame);
            registerNotificationHandler(BusinessActions.INVITE_USER, handleInviteUser);
            registerNotificationHandler(BusinessActions.JOIN_TO_GAME, handleJoinToGame);
            registerNotificationHandler(BusinessActions.CHANGE_INVITATION_STATUS, handleChangeInvitationStatus);
            registerNotificationHandler(BusinessActions.EDIT_INVITATIONS, handleEditInvitations);
        }

        private function handleChangeInvitationStatus(invitationStatus:ChangeInvitationStatusDto):void {
            gameServiceProxy.changeInvitationStatus(invitationStatus, changeInvitationStatusCallback);
        }

        private function handleJoinToGame(gameId:Number):void {
            gameServiceProxy.joinToGame(gameId, joinToGameCallback)
        }

        private function handleInviteUser(invitationDto:CreateInvitationDto):void {
            gameServiceProxy.inviteUser(invitationDto, function():void {
                sendNotification(Notifications.INVITATION_SENT, invitationDto.inviteeNames);
            });
        }

        private function handleCancelGame(gameId:Number):void {
            gameServiceProxy.cancelGame(gameId, cancelGameCallback);
        }

        private function handleInitiateGame(gameVariantSelection:GameInitiationDto):void {
            gameServiceProxy.initiateGame(gameVariantSelection, function(result:GameStatusDto):void {
                if (gameVariantSelection.offer) {
                    sendNotification(Notifications.GAME_INITIATED, result);
                } else {
                    sendNotification(BusinessActions.SHOW_INVITATIONS_PAGE);
                }
            });
        }

        private function handleEditInvitations(editInvitationsDto:EditInvitationsDto):void {
            if (editInvitationsDto.createInvitationDto != null) {
                gameServiceProxy.inviteUser(editInvitationsDto.createInvitationDto,
                        function():void {
                            if (editInvitationsDto.changeInvitationStatusDto != null) {
                                gameServiceProxy.changeInvitationStatus(editInvitationsDto.changeInvitationStatusDto,
                                        function():void { sendNotification(BusinessActions.SHOW_INVITATIONS_PAGE) });
                            } else {
                                sendNotification(BusinessActions.SHOW_INVITATIONS_PAGE);
                            }
                        });
            } else if (editInvitationsDto.changeInvitationStatusDto != null) {
                gameServiceProxy.changeInvitationStatus(editInvitationsDto.changeInvitationStatusDto,
                        function():void { sendNotification(BusinessActions.SHOW_INVITATIONS_PAGE) });
            } else {
                sendNotification(BusinessActions.SHOW_INVITATIONS_PAGE);
            }
        }

        private function cancelGameCallback():void {
            sendNotification(Notifications.REFRESH_CURRENT_PAGE);
        }

        private function joinToGameCallback(gameStatus:GameStatusDto):void {
            if (gameStatus.gameStatus == GameDto.RUNNING) {
                sendNotification(BusinessActions.LOAD_GAME, gameStatus.gameId);
                sendNotification(Notifications.SHOW_MY_GAMES_PAGE);
            } else {
                sendNotification(Notifications.REFRESH_CURRENT_PAGE);
            }
        }

        private function changeInvitationStatusCallback(result:Object):void {
            sendNotification(Notifications.REFRESH_CURRENT_PAGE);
        }
    }
}

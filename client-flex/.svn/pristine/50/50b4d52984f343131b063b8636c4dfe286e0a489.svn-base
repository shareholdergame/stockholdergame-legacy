package com.stockholdergame.client.mvc.facade {
    import com.stockholdergame.client.mvc.controller.GameListCommand;
    import com.stockholdergame.client.mvc.controller.InitializeUserSessionCommand;
    import com.stockholdergame.client.mvc.controller.LoginCommand;
    import com.stockholdergame.client.mvc.controller.NewGameCommand;
    import com.stockholdergame.client.mvc.controller.PlayGameCommand;
    import com.stockholdergame.client.mvc.controller.PlaySoundCommand;
    import com.stockholdergame.client.mvc.controller.ProfileCommand;
    import com.stockholdergame.client.mvc.controller.StartupCommand;
    import com.stockholdergame.client.mvc.controller.SummaryCommand;
    import com.stockholdergame.client.mvc.controller.SearchUserCommand;
    import com.stockholdergame.client.mvc.controller.ChatCommand;
    import com.stockholdergame.client.ui.events.BusinessActions;

    import mx.core.Application;

    import org.puremvc.as3.patterns.facade.Facade;

    public class ApplicationFacade extends Facade {

        public static function getInstance():ApplicationFacade {
            if (instance == null) {
                instance = new ApplicationFacade();
            }
            return instance as ApplicationFacade;
        }

        override protected function initializeController():void {
            super.initializeController();
            registerCommand(Notifications.STARTUP, StartupCommand);

            registerCommand(BusinessActions.SIGN_IN, LoginCommand);
            registerCommand(BusinessActions.SIGN_OUT, LoginCommand);
            registerCommand(BusinessActions.RESTORE_PASSWORD, LoginCommand);
            registerCommand(BusinessActions.SIGN_UP, LoginCommand);
            registerCommand(BusinessActions.CHECK_USER_NAME_EXISTENCE, LoginCommand);
            registerCommand(BusinessActions.CHECK_EMAIL_USAGE, LoginCommand);
            registerCommand(BusinessActions.GET_CAPTCHA, LoginCommand);
            registerCommand(BusinessActions.SET_UNAUTH_LANGUAGE, LoginCommand);

            registerCommand(BusinessActions.INIT_USER_SESSION, InitializeUserSessionCommand);

            registerCommand(BusinessActions.SAVE_PROFILE, ProfileCommand);
            registerCommand(BusinessActions.CHANGE_PASSWORD, ProfileCommand);
            registerCommand(BusinessActions.CHANGE_USER_NAME, ProfileCommand);
            registerCommand(BusinessActions.CHANGE_EMAIL, ProfileCommand);
            registerCommand(BusinessActions.REMOVE_ACCOUNT, ProfileCommand);
            registerCommand(BusinessActions.RESTORE_ACCOUNT, ProfileCommand);
            registerCommand(BusinessActions.GET_FRIENDS, ProfileCommand);
            registerCommand(BusinessActions.GET_FRIEND_REQUESTS, ProfileCommand);
            registerCommand(BusinessActions.GET_FRIEND_REQUESTS_FOR_HOME_PAGE, ProfileCommand);
            registerCommand(BusinessActions.CONFIRM_OPERATION, ProfileCommand);
            registerCommand(BusinessActions.RESEND_VERIFICATION_EMAIL, ProfileCommand);
            registerCommand(BusinessActions.CANCEL_ACCOUNT_OPERATION, ProfileCommand);
            registerCommand(BusinessActions.SEND_FRIEND_REQUEST, ProfileCommand);
            registerCommand(BusinessActions.CHANGE_FRIEND_REQUEST_STATUS, ProfileCommand);
            registerCommand(BusinessActions.CANCEL_FRIEND_REQUEST, ProfileCommand);
            registerCommand(BusinessActions.CHANGE_LANGUAGE, ProfileCommand);
            registerCommand(BusinessActions.UPDATE_AVATAR, ProfileCommand);

            registerCommand(BusinessActions.GET_SUMMARY, SummaryCommand);
            registerCommand(BusinessActions.GET_SERVER_STATISTICS, SummaryCommand);
            registerCommand(BusinessActions.GET_LAST_EVENTS, SummaryCommand);

            registerCommand(BusinessActions.INITIATE_GAME, NewGameCommand);
            registerCommand(BusinessActions.CANCEL_GAME, NewGameCommand);
            registerCommand(BusinessActions.JOIN_TO_GAME, NewGameCommand);
            registerCommand(BusinessActions.INVITE_USER, NewGameCommand);
            registerCommand(BusinessActions.CHANGE_INVITATION_STATUS, NewGameCommand);
            registerCommand(BusinessActions.EDIT_INVITATIONS, NewGameCommand);

            registerCommand(BusinessActions.GET_GAME_VARIANT_SUMMARY, GameListCommand);
            registerCommand(BusinessActions.GET_OPEN_GAMES, GameListCommand);
            registerCommand(BusinessActions.GET_GAME_OFFERS, GameListCommand);
            registerCommand(BusinessActions.GET_GAMES_IN_PROGRESS, GameListCommand);
            registerCommand(BusinessActions.GET_INVITATIONS_INCOMING, GameListCommand);
            registerCommand(BusinessActions.GET_INVITATIONS_OUTGOING, GameListCommand);
            registerCommand(BusinessActions.GET_FINISHED_GAMES, GameListCommand);
            registerCommand(BusinessActions.GET_SCORES, GameListCommand);
            registerCommand(BusinessActions.GET_TOTAL_SCORE, GameListCommand);
            registerCommand(BusinessActions.GET_ALL_GAME_OFFERS, GameListCommand);
            registerCommand(BusinessActions.GET_INVITATIONS_FOR_HOME_PAGE, GameListCommand);
            registerCommand(BusinessActions.GET_GAMES_IN_PROGRESS_FOR_HOME_PAGE, GameListCommand);

            registerCommand(BusinessActions.LOAD_GAME, PlayGameCommand);
            registerCommand(BusinessActions.DO_MOVE, PlayGameCommand);

            registerCommand(BusinessActions.FILTER_USERS, SearchUserCommand);
            registerCommand(BusinessActions.FILTER_INVITED_USERS, SearchUserCommand);
            registerCommand(BusinessActions.GET_USER_STATISTICS, SearchUserCommand);
            registerCommand(BusinessActions.GET_TOP10, SearchUserCommand);

            registerCommand(BusinessActions.SEND_MESSAGE, ChatCommand);
            registerCommand(BusinessActions.GET_CHAT_HISTORY, ChatCommand);
            registerCommand(BusinessActions.MARK_CHAT_MESSAGES_AS_READ, ChatCommand);
            registerCommand(BusinessActions.CLEAR_CHAT_HISTORY, ChatCommand);
            registerCommand(BusinessActions.GET_UNREAD_CHAT_MESSAGES, ChatCommand);
            registerCommand(BusinessActions.SHOW_GAME_CHAT_WINDOW, ChatCommand);
            registerCommand(BusinessActions.GET_CHATS, ChatCommand);

            registerCommand(Notifications.PLAY_SOUND, PlaySoundCommand);
        }

        public function startup(application:Application):void {
            sendNotification(Notifications.STARTUP, application);
        }
    }
}

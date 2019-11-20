package com.stockholdergame.client.ui.components.list.listClasses {
    import com.stockholdergame.client.model.dto.account.UserFilterDto;

    [Bindable]
    public final class UserDialogMode {

        public function UserDialogMode(defaultFilter:UserFilterDto,
                                       showInvitationButton:Boolean = false,
                                       showFriendFunctionButtons:Boolean = false,
                                       disableFriendFilter:Boolean = false) {
            this.defaultFilter = defaultFilter;
            this.showInvitationButton = showInvitationButton;
            this.showFriendFunctionButtons = showFriendFunctionButtons;
            this.disableFriendFilter = disableFriendFilter;
        }

        public static const INVITATION_MODE:UserDialogMode = new UserDialogMode(defaultFilterForInvitationMode, true);
        public static const SEARCH_FRIEND_MODE:UserDialogMode = new UserDialogMode(defaultFilterForSearchFriendMode, false, true, true);

        public var showInvitationButton:Boolean;
        public var showFriendFunctionButtons:Boolean;
        public var disableFriendFilter:Boolean;
        public var defaultFilter:UserFilterDto;

        private static function get defaultFilterForInvitationMode():UserFilterDto {
            var df:UserFilterDto = new UserFilterDto();
            df.friendFilters = [UserFilterDto.FRIENDS_ONLY];
            return df;
        }

        private static function get defaultFilterForSearchFriendMode():UserFilterDto {
            var df:UserFilterDto = new UserFilterDto();
            df.friendFilters = [UserFilterDto.OTHER];
            return df;
        }
    }
}

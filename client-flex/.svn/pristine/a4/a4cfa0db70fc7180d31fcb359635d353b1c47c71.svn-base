package com.stockholdergame.client.model.dto.account {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.MyAccountDto")]
    public class MyAccountDto extends UserInfoDto {
        public function MyAccountDto() {
        }

        public static const ACCOUNT_STATUS_NEW:String = "NEW";
        public static const ACCOUNT_STATUS_ACTIVE:String = "ACTIVE";
        public static const ACCOUNT_STATUS_REMOVED:String = "REMOVED";
        public static const ACCOUNT_STATUS_REMOVED_COMPLETELY:String = "REMOVED_COMPLETELY";

        public var email:String;
        public var status:String;
        public var registrationDate:Date;
        public var subtopicName:String;
        public var accountOperations:ArrayCollection;

        public var isEmailChangeOperationExists:Boolean = false;
        public var newEmail:String;
        public var isStatusChangeOperationExists:Boolean = false;
        public var newStatus:String;

        public function hasActivationOperation():Boolean {
            if (accountOperations != null) {
                for each (var accountOperationDto:AccountOperationDto in accountOperations) {
                    if (accountOperationDto.newValue == MyAccountDto.ACCOUNT_STATUS_ACTIVE && !accountOperationDto.confirmed) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}

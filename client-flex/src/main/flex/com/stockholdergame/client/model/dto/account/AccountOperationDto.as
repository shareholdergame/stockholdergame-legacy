package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.AccountOperationDto")]
    public class AccountOperationDto {
        public function AccountOperationDto() {
        }

        public static const OPER_TYPE_STATUS_CHANGED:String = "STATUS_CHANGED";
        public static const OPER_TYPE_EMAIL_CHANGED:String = "EMAIL_CHANGED";

        public var operationType:String;
        public var oldValue:String;
        public var newValue:String;
        public var initiationDate:Date;
        public var expirationDate:Date;

        public var operationDescriptionKey:String;
        public var confirmed:Boolean = false;
    }
}

package com.stockholdergame.client.model.dto.account {

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.OperationTypeDto")]
    public class OperationTypeDto {
        public function OperationTypeDto(operationType:String) {
            this.operationType = operationType;
        }

        public var operationType:String;
    }
}

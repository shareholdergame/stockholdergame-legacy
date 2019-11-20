package com.stockholdergame.client.model.dto.account {
    import mx.collections.ArrayCollection;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.account.UsersList")]
    public class UsersList {
        public function UsersList() {
        }

        public var totalCount:int;
        public var users:ArrayCollection;
    }
}

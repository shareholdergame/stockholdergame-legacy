package com.stockholdergame.client.model.dto.account {
    import flash.utils.ByteArray;

    public interface UserViewDto {

        function get userName():String;

        function get avatar():ByteArray;

        function get bot():Boolean;

        function get locale():String;
    }
}

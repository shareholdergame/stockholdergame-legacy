package com.stockholdergame.client.ui.components.page {
    import com.stockholdergame.client.model.dto.game.GameDto;

    public interface IGamePage {

        function get game():GameDto;

        function set game(value:GameDto):void;
    }
}

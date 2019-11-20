package com.stockholdergame.client.ui.components.page.pageClasses {
    import com.stockholdergame.client.model.dto.game.lite.GameLiteDto;

    [Bindable]
    public class SearchUserPageData {

        public function SearchUserPageData(state:String, game:GameLiteDto) {
            this.state = state;
            this.game = game;
        }

        public var state:String;
        public var game:GameLiteDto;
    }
}

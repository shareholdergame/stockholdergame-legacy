package com.stockholdergame.client.model.dto.game {
    import com.stockholdergame.client.model.dto.PaginationDto;

    [Bindable]
    [RemoteClass(alias="com.stockholdergame.server.dto.game.UserStatisticsFilterDto")]
    public class UserStatisticsFilterDto extends PaginationDto {
        public function UserStatisticsFilterDto() {
        }

        public var userName:String;
        public var statisticsVariant:String = "Statistics10";
        public var showTop10:Boolean = false;
    }
}

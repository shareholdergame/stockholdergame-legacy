package com.stockholdergame.client.ui.message {
    import com.stockholdergame.client.ui.IconResources;

    public final class MessageSeverity {
        public static const INFO:MessageSeverity = new MessageSeverity(IconResources.INFO_IMG, "infoPanel");
        public static const WARNING:MessageSeverity = new MessageSeverity(IconResources.WARNING_IMG, "warnPanel");
        public static const ERROR:MessageSeverity = new MessageSeverity(IconResources.ERROR_IMG, "errorPanel");
        public static const NOTIFICATION:MessageSeverity = new MessageSeverity(IconResources.NO_AVATAR_IMG, "messagePanel");

        function MessageSeverity(imageSource:Class, styleName:String) {
            this.imageSource = imageSource;
            this.styleName = styleName;
        }

        public var imageSource:Class;

        public var styleName:String;
    }
}

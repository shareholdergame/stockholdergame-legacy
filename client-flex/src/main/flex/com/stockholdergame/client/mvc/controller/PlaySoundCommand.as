package com.stockholdergame.client.mvc.controller {
    import com.stockholdergame.client.model.dto.game.event.UserNotification;
    import com.stockholdergame.client.mvc.facade.Notifications;
    import com.stockholdergame.client.util.SoundUtil;

    public class PlaySoundCommand extends DispatchCommand {

        [Embed(source="/sounds/move-done.mp3")]
        private var moveDoneSound:Class;

        [Embed(source="/sounds/incoming-message.mp3")]
        private var incomingMessageSound:Class;

        [Embed(source="/sounds/bell.mp3")]
        private var startFinishGameSound:Class;

        private var actionSoundMapping:Array = [
            {sound:moveDoneSound, action:Notifications.MOVE_DONE_NOTIFICATION, off:false},
            {sound:incomingMessageSound, action:Notifications.INCREASE_NEW_MESSAGES_COUNTER, off:false},
            {sound:startFinishGameSound, action:Notifications.GAME_FINISHED_NOTIFICATION, off:true},
            {sound:startFinishGameSound, action:Notifications.GAME_LOADED, off:true}
        ];

        public function PlaySoundCommand() {
            registerNotificationHandler(Notifications.PLAY_SOUND, handlePlaySound);
        }

        private function handlePlaySound(action:String):void {
            for each (var obj:Object in actionSoundMapping) {
                if (obj.action == action && !obj.off) {
                    SoundUtil.playSound(obj.sound);
                }
            }
        }
    }
}

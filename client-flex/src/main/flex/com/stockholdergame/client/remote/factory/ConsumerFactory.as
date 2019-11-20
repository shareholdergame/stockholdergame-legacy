package com.stockholdergame.client.remote.factory {
    import com.stockholdergame.client.configuration.ApplicationConfiguration;
    import com.stockholdergame.client.configuration.Environment;

    import mx.messaging.Channel;
    import mx.messaging.ChannelSet;
    import mx.messaging.Consumer;
    import mx.messaging.channels.AMFChannel;
    import mx.messaging.channels.SecureAMFChannel;
    import mx.messaging.events.MessageEvent;
    import mx.messaging.events.MessageFaultEvent;

    public class ConsumerFactory {

        private static var _instance:ConsumerFactory;

        function ConsumerFactory() {
        }

        public static function get instance():ConsumerFactory {
            if (_instance == null) {
                _instance = new ConsumerFactory();
                _instance.initialize();
            }
            return _instance;
        }

        private var channelSet:ChannelSet;

        private function initialize():void {
            var appConf:ApplicationConfiguration = ApplicationConfiguration.instance;
            channelSet = new ChannelSet();
            var env:Environment = appConf.activeEnvironment;
            var channel:Channel;
            if (env.sslEnabled) {
                 channel = new SecureAMFChannel(env.pollingChannelId, env.pollingChannelUrl);
            } else {
                channel = new AMFChannel(env.pollingChannelId, env.pollingChannelUrl);
            }
            channelSet.addChannel(channel);
        }

        public function getConsumer(destination:String,
                                    subtopic:String,
                                    onMessageHandler:Function,
                                    onMessageFaultHandler:Function):Consumer {
            var consumer:Consumer = new Consumer();
            consumer.channelSet = channelSet;
            consumer.destination = destination;
            consumer.subtopic = subtopic;
            consumer.resubscribeAttempts = 10;
            consumer.resubscribeInterval = 10000;
            consumer.addEventListener(MessageEvent.MESSAGE, onMessageHandler);
            consumer.addEventListener(MessageFaultEvent.FAULT, onMessageFaultHandler);

            consumer.subscribe();

            return consumer;
        }

        public function disconnectChannelSet():void {
            channelSet.disconnectAll();
        }
    }
}

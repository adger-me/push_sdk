package me.adger.pushsdk;

import android.content.Context;

import com.mob.pushsdk.MobPush;
import com.mob.pushsdk.MobPushCustomMessage;
import com.mob.pushsdk.MobPushNotifyMessage;
import com.mob.pushsdk.MobPushReceiver;
import com.mob.tools.utils.Hashon;

import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * MobpushPlugin
 */
public class MobpushReceiverPlugin implements EventChannel.StreamHandler {
    private static MobPushReceiver mobPushReceiver;

    private Hashon hashon = new Hashon();

    public static MobPushReceiver getMobPushReceiver(){
        return mobPushReceiver;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(BinaryMessenger messenger) {
        final EventChannel channel = new EventChannel(messenger, "mobpush_receiver");
        channel.setStreamHandler(new MobpushReceiverPlugin());
    }

    private MobPushReceiver createMobPushReceiver(final EventChannel.EventSink event) {
        mobPushReceiver = new MobPushReceiver() {
            @Override
            public void onCustomMessageReceive(Context context, MobPushCustomMessage mobPushCustomMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 0);
                map.put("result", hashon.fromJson(hashon.fromObject(mobPushCustomMessage)));
                event.success(hashon.fromHashMap(map));
            }

            @Override
            public void onNotifyMessageReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 1);
                map.put("result", hashon.fromJson(hashon.fromObject(mobPushNotifyMessage)));
                event.success(hashon.fromHashMap(map));
            }

            @Override
            public void onNotifyMessageOpenedReceive(Context context, MobPushNotifyMessage mobPushNotifyMessage) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("action", 2);
                map.put("result", hashon.fromJson(hashon.fromObject(mobPushNotifyMessage)));
                event.success(hashon.fromHashMap(map));
            }

            @Override
            public void onTagsCallback(Context context, String[] tags, int operation, int errorCode) {

            }

            @Override
            public void onAliasCallback(Context context, String alias, int operation, int errorCode) {

            }
        };
        return mobPushReceiver;
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        mobPushReceiver = createMobPushReceiver(eventSink);
        MobPush.addPushReceiver(mobPushReceiver);
    }

    @Override
    public void onCancel(Object o) {

    }
}

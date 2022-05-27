package go.meethour.io.react.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import go.meethour.io.MeetHourSDK.android.BroadcastEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MeetHourViewManager extends SimpleViewManager<RNMeetHourView> {
  public static final String REACT_CLASS = "MeetHourView";

  private final ReactApplicationContext reactApplicationContext;
  private RNMeetHourView mHMeetView;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      onBroadcastReceived(intent);
    }
  };

  public MeetHourViewManager(ReactApplicationContext reactApplicationContext) {
    this.reactApplicationContext = reactApplicationContext;
  }

  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @NonNull
  @Override
  protected RNMeetHourView createViewInstance(@NonNull ThemedReactContext reactContext) {
    mHMeetView = new RNMeetHourView(reactContext.getCurrentActivity());

    registerForBroadcastMessages();

    return mHMeetView;
  }

  @Override
  public void onDropViewInstance(@NonNull RNMeetHourView view) {
    LocalBroadcastManager.getInstance(mHMeetView.getContext()).unregisterReceiver(broadcastReceiver);

    mHMeetView.leave();
    mHMeetView.dispose();
  }

  @ReactProp(name = "options")
  public void setOptions(RNMeetHourView view, ReadableMap options) {
    RNMeetHourConferenceOptions.Builder builder = new RNMeetHourConferenceOptions.Builder();

    if (options.hasKey("room")) {
      builder.setRoom(options.getString("room"));
    } else {
      throw new RuntimeException("Room must not be empty");
    }

    try {
      builder.setServerURL(
        new URL(options.hasKey("serverUrl") ? options.getString("serverUrl") : "https://meethour.io"));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Server url invalid");
    }

    if (options.hasKey("userInfo")) {
      ReadableMap userInfoMap = options.getMap("userInfo");

      if (userInfoMap != null) {
        RNMeetHourUserInfo userInfo = new RNMeetHourUserInfo();

        if (userInfoMap.hasKey("displayName")) {
          userInfo.setDisplayName(userInfoMap.getString("displayName"));
        }

        if (userInfoMap.hasKey("email")) {
          userInfo.setEmail(userInfoMap.getString("email"));
        }

        if (userInfoMap.hasKey("avatar")) {
          try {
            userInfo.setAvatar(new URL(userInfoMap.getString("avatar")));
          } catch (MalformedURLException e) {
            throw new RuntimeException("Avatar url invalid");
          }
        }

        builder.setUserInfo(userInfo);
      }
    }

    if (options.hasKey("token")) {
      builder.setToken(options.getString("token"));
    }

    // Set built-in config overrides
    if (options.hasKey("subject")) {
      builder.setSubject(options.getString("subject"));
    }

    if (options.hasKey("audioOnly")) {
      builder.setAudioOnly(options.getBoolean("audioOnly"));
    }

    if (options.hasKey("audioMuted")) {
      builder.setAudioMuted(options.getBoolean("audioMuted"));
    }
    
    if (options.hasKey("videoMuted")) {
      builder.setVideoMuted(options.getBoolean("videoMuted"));
    }

    // Set the feature flags
    if (options.hasKey("featureFlags")) {
      ReadableMap featureFlags = options.getMap("featureFlags");
      ReadableMapKeySetIterator iterator = featureFlags.keySetIterator();
      while (iterator.hasNextKey()) {
        String flag = iterator.nextKey();
        Boolean value = featureFlags.getBoolean(flag);
        builder.setFeatureFlag(flag, value);
      }
    }

    RNMeetHourConferenceOptions mHMeetConferenceOptions = builder.build();

    mHMeetView.join(mHMeetConferenceOptions);
  }

  private void registerForBroadcastMessages() {
    IntentFilter intentFilter = new IntentFilter();

    for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
      intentFilter.addAction(type.getAction());
    }

    LocalBroadcastManager.getInstance(mHMeetView.getContext()).registerReceiver(broadcastReceiver, intentFilter);
  }

  private void onBroadcastReceived(Intent intent) {
    if (intent != null) {

      BroadcastEvent event = new BroadcastEvent(intent);
      WritableMap eventMap = Arguments.createMap();

      switch (event.getType()) {
        case CONFERENCE_JOINED:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            mHMeetView.getId(),
            "onConferenceJoined",
            eventMap);
          break;
        case CONFERENCE_TERMINATED:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            mHMeetView.getId(),
            "onConferenceTerminated",
            eventMap);

	  // The onConferenceTerminatedReceiver in MeetHourModule relies
	  // on this broadcast event to resolve the launchMeetHourView promise.
          Intent conferenceTerminatedBroadcast = new Intent(event.getType().getAction());
          reactApplicationContext.sendBroadcast(conferenceTerminatedBroadcast);
          break;
        case CONFERENCE_WILL_JOIN:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            mHMeetView.getId(),
            "onConferenceWillJoin",
            eventMap);
          break;
      }
    }
  }

  @Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
    builder.put("onConferenceJoined", MapBuilder.of("registrationName", "onConferenceJoined"));
    builder.put("onConferenceTerminated", MapBuilder.of("registrationName", "onConferenceTerminated"));
    builder.put("onConferenceWillJoin", MapBuilder.of("registrationName", "onConferenceWillJoin"));
    return builder.build();
  }
}

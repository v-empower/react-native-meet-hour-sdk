package go.meethour.io.react.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.module.annotations.ReactModule;

import go.meethour.io.MeetHourSDK.android.BroadcastAction;
import go.meethour.io.MeetHourSDK.android.BroadcastEvent;
import go.meethour.io.MeetHourSDK.android.MeetHourConferenceOptions;
import go.meethour.io.MeetHourSDK.android.MeetHourUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

@ReactModule(name = MeetHourModule.NAME)
public class MeetHourModule extends ReactContextBaseJavaModule {
  public static final String NAME = "MeetHour";

  private BroadcastReceiver onConferenceTerminatedReceiver;

  public MeetHourModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void hangUp() {
    Intent hangUpBroadcastIntent = new Intent("go.meethour.io.HANG_UP");
    LocalBroadcastManager.getInstance(getReactApplicationContext()).sendBroadcast(hangUpBroadcastIntent);
  }

  @ReactMethod
  public void launchMeetHourView(ReadableMap options, Promise onConferenceTerminated) {
    MeetHourConferenceOptions.Builder builder = new MeetHourConferenceOptions.Builder();

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
        MeetHourUserInfo userInfo = new MeetHourUserInfo();

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

    if (options.hasKey("pcode")) {
      builder.setPcode(options.getString("pcode"));
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

    if (options.hasKey("prejoinPageEnabled")) {
      builder.setPrejoinPageEnabled(options.getBoolean("prejoinPageEnabled"));
    }

    if (options.hasKey("disableInviteFunctions")) {
      builder.setDisableInviteFunctions(options.getBoolean("disableInviteFunctions"));
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

    MeetHourActivityExtended.launchExtended(getReactApplicationContext(), builder.build());

    this.registerOnConferenceTerminatedListener(onConferenceTerminated);
  }

  @ReactMethod
  public void launch(ReadableMap options, Promise onConferenceTerminated) {
    launchMeetHourView(options, onConferenceTerminated);
  }

  private void registerOnConferenceTerminatedListener(Promise onConferenceTerminated) {
    onConferenceTerminatedReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);

        onConferenceTerminated.resolve(null);

        LocalBroadcastManager.getInstance(getReactApplicationContext()).unregisterReceiver(onConferenceTerminatedReceiver);
      }
    };

    IntentFilter intentFilter = new IntentFilter(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());

    LocalBroadcastManager.getInstance(getReactApplicationContext()).registerReceiver(this.onConferenceTerminatedReceiver, intentFilter);
  }

  @Override    
  public boolean canOverrideExistingModule() {        
    return true;    
  }
}

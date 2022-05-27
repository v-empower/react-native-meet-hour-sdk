package go.meethour.io.react.sdk;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;

import go.meethour.io.MeetHourSDK.android.BaseReactView;
import go.meethour.io.MeetHourSDK.android.MeetHourViewListener;
import go.meethour.io.MeetHourSDK.android.ListenerUtils;
import go.meethour.io.MeetHourSDK.android.log.MeetHourLogger;

import java.lang.reflect.Method;
import java.util.Map;

public class RNMeetHourView extends BaseReactView<MeetHourViewListener> implements RNOngoingConferenceTracker.OngoingConferenceListener {

  private static final Map<String, Method> LISTENER_METHODS = ListenerUtils.mapListenerMethods(MeetHourViewListener.class);

  private volatile String url;

  private static Bundle mergeProps(@Nullable Bundle a, @Nullable Bundle b) {
    Bundle result = new Bundle();

    if (a == null) {
      if (b != null) {
        result.putAll(b);
      }

      return result;
    }

    if (b == null) {
      result.putAll(a);

      return result;
    }

    // Start by putting all of a in the result.
    result.putAll(a);

    // Iterate over each key in b and override if appropriate.
    for (String key : b.keySet()) {
      Object bValue = b.get(key);
      Object aValue = a.get(key);
      String valueType = bValue.getClass().getSimpleName();

      if (valueType.contentEquals("Boolean")) {
        result.putBoolean(key, (Boolean) bValue);
      } else if (valueType.contentEquals("String")) {
        result.putString(key, (String) bValue);
      } else if (valueType.contentEquals("Bundle")) {
        result.putBundle(key, mergeProps((Bundle) aValue, (Bundle) bValue));
      } else {
        throw new RuntimeException("Unsupported type: " + valueType);
      }
    }

    return result;
  }

  public RNMeetHourView(@NonNull Context context) {
    super(context);

    RNOngoingConferenceTracker.getInstance().addListener(this);
  }

  @Override
  public void dispose() {
    RNOngoingConferenceTracker.getInstance().removeListener(this);
    super.dispose();
  }

  public void enterPictureInPicture() {
    MeetHourLogger.e("PiP not supported");
  }

  public void join(@Nullable RNMeetHourConferenceOptions options) {
    setProps(options != null ? options.asProps() : new Bundle());
  }

  public void leave() {
    setProps(new Bundle());
  }

  private void setProps(@NonNull Bundle newProps) {
    Bundle props = mergeProps(new Bundle(), newProps);

    props.putLong("timestamp", System.currentTimeMillis());

    createReactRootView("App", props);
  }

  @Override
  public void onCurrentConferenceChanged(String conferenceUrl) {
    this.url = conferenceUrl;
  }

  @Override
  @Deprecated
  protected void onExternalAPIEvent(String name, ReadableMap data) {
    onExternalAPIEvent(LISTENER_METHODS, name, data);
  }

  @Override
  protected void onDetachedFromWindow() {
    dispose();
    super.onDetachedFromWindow();
  }
}

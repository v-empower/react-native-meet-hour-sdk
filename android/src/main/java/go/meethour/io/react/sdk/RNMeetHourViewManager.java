package go.meethour.io.react.sdk;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.module.annotations.ReactModule;

import go.meethour.io.MeetHourSDK.android.MeetHourViewListener;

import java.util.Map;

import static java.security.AccessController.getContext;

@ReactModule(name = RNMeetHourViewManager.REACT_CLASS)
public class RNMeetHourViewManager extends SimpleViewManager<RNMeetHourView> implements MeetHourViewListener {
    public static final String REACT_CLASS = "RNMeetHourView";
    private IRNMeetHourViewReference mMeetHourViewReference;
    private ReactApplicationContext mReactContext;

    public RNMeetHourViewManager(ReactApplicationContext reactContext, IRNMeetHourViewReference meetHourViewReference) {
        mMeetHourViewReference = meetHourViewReference;
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public RNMeetHourView createViewInstance(ThemedReactContext context) {
        if (mMeetHourViewReference.getMeetHourView() == null) {
            RNMeetHourView view = new RNMeetHourView(context.getCurrentActivity());
            view.setListener(this);
            mMeetHourViewReference.setMeetHourView(view);
        }
        return mMeetHourViewReference.getMeetHourView();
    }

    public void onConferenceJoined(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetHourViewReference.getMeetHourView().getId(),
                "conferenceJoined",
                event);
    }

    public void onConferenceTerminated(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        event.putString("error", (String) data.get("error"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetHourViewReference.getMeetHourView().getId(),
                "conferenceTerminated",
                event);
    }

    public void onConferenceWillJoin(Map<String, Object> data) {
        WritableMap event = Arguments.createMap();
        event.putString("url", (String) data.get("url"));
        mReactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                mMeetHourViewReference.getMeetHourView().getId(),
                "conferenceWillJoin",
                event);
    }

    public Map getExportedCustomBubblingEventTypeConstants() {
        return MapBuilder.builder()
                .put("conferenceJoined", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceJoined")))
                .put("conferenceTerminated", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceTerminated")))
                .put("conferenceWillJoin", MapBuilder.of("phasedRegistrationNames", MapBuilder.of("bubbled", "onConferenceWillJoin")))
                .build();
    }
}
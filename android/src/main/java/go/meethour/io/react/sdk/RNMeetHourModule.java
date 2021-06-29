package go.meethour.io.react.sdk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.module.annotations.ReactModule;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

@ReactModule(name = RNMeetHourModule.MODULE_NAME)
public class RNMeetHourModule extends ReactContextBaseJavaModule {
    public static final String MODULE_NAME = "RNMeetHourModule";
    private final IRNMeetHourViewReference mMeetHourViewReference;

    public String meetHourServerUrl = "https://meethour.io";

    public RNMeetHourModule(ReactApplicationContext reactContext, IRNMeetHourViewReference meetHourViewReference) {
        super(reactContext);
        mMeetHourViewReference = meetHourViewReference;
    }

    @Override
    public @NotNull String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void initialize() {
        Timber.d("Initialize is deprecated in v2");
    }

    @ReactMethod
    public void join_meethour(ReadableMap userInfo) {
        RNMeetHourUserInfo _userInfo = new RNMeetHourUserInfo();
        UiThreadUtil.runOnUiThread(() -> {

            if (mMeetHourViewReference.getMeetHourView() != null) {
                mMeetHourViewReference.getMeetHourView().leave();
                mMeetHourViewReference.getMeetHourView().dispose();
            }

            String roomName = String.valueOf(Math.random());
            String subject = "";
            boolean audioMuted = false;
            boolean videoMuted = false;
            boolean chatEnabled = true;
            boolean addPeopleEnabled = true;
            boolean inviteEnabled = true;
            boolean meetingNameEnabled = true;
            boolean conferenceTimerEnabled = true;
            boolean raiseHandEnabled = false;
            boolean recordingEnabled = false;
            boolean liveStreamEnabled = false;
            boolean toolBoxEnabled = true;
            boolean toolBoxAlwaysVisible = true;
            boolean meetingPasswordEnabled = true;
            boolean pipModeEnabled = true;
            URL serverUrl = null;
            try {
                serverUrl = new URL(meetHourServerUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if (userInfo != null) {
                System.out.println("options are not null");

                if (userInfo.hasKey("serverUrl") && userInfo.getString("serverUrl") != null) {
                    try {
                        serverUrl = new URL(userInfo.getString("serverUrl"));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                if (userInfo.hasKey("audioMuted")) {
                    if (userInfo.getBoolean("audioMuted") || (!userInfo.getBoolean("audioMuted"))) {
                        audioMuted = userInfo.getBoolean("audioMuted");
                    }
                }

                if (userInfo.hasKey("videoMuted")) {
                    if (userInfo.getBoolean("videoMuted") || (!userInfo.getBoolean("videoMuted"))) {
                        videoMuted = userInfo.getBoolean("videoMuted");
                    }
                }

                if (userInfo.hasKey("roomName") && userInfo.getString("roomName") != null) {
                    roomName = userInfo.getString("roomName");
                }

                if (userInfo.hasKey("subject") && userInfo.getString("subject") != null) {
                    subject = userInfo.getString("subject");
                }

                if (userInfo.hasKey("chatEnabled")) {
                    if (userInfo.getBoolean("chatEnabled") || (!userInfo.getBoolean("chatEnabled"))) {
                        chatEnabled = userInfo.getBoolean("chatEnabled");
                    }
                }

                if (userInfo.hasKey("addPeopleEnabled")) {
                    if (userInfo.getBoolean("addPeopleEnabled") || (!userInfo.getBoolean("addPeopleEnabled"))) {
                        addPeopleEnabled = userInfo.getBoolean("addPeopleEnabled");
                    }
                }

                if (userInfo.hasKey("inviteEnabled")) {
                    if (userInfo.getBoolean("inviteEnabled") || (!userInfo.getBoolean("inviteEnabled"))) {
                        inviteEnabled = userInfo.getBoolean("inviteEnabled");
                    }
                }

                if (userInfo.hasKey("meetingNameEnabled")) {
                    if (userInfo.getBoolean("meetingNameEnabled") || (!userInfo.getBoolean("meetingNameEnabled"))) {
                        meetingNameEnabled = userInfo.getBoolean("meetingNameEnabled");
                    }
                }

                if (userInfo.hasKey("conferenceTimerEnabled")) {
                    if (userInfo.getBoolean("conferenceTimerEnabled") || (!userInfo.getBoolean("conferenceTimerEnabled"))) {
                        conferenceTimerEnabled = userInfo.getBoolean("conferenceTimerEnabled");
                    }
                }

                if (userInfo.hasKey("raiseHandEnabled")) {
                    if (userInfo.getBoolean("raiseHandEnabled") || (!userInfo.getBoolean("raiseHandEnabled"))) {
                        raiseHandEnabled = userInfo.getBoolean("raiseHandEnabled");
                    }
                }

                if (userInfo.hasKey("recordingEnabled")) {
                    if (userInfo.getBoolean("recordingEnabled") || (!userInfo.getBoolean("recordingEnabled"))) {
                        recordingEnabled = userInfo.getBoolean("recordingEnabled");
                    }
                }

                if (userInfo.hasKey("liveStreamEnabled")) {
                    if (userInfo.getBoolean("liveStreamEnabled") || (!userInfo.getBoolean("liveStreamEnabled"))) {
                        liveStreamEnabled = userInfo.getBoolean("liveStreamEnabled");
                    }
                }

                if (userInfo.hasKey("toolBoxEnabled")) {
                    if (userInfo.getBoolean("toolBoxEnabled") || (!userInfo.getBoolean("toolBoxEnabled"))) {
                        toolBoxEnabled = userInfo.getBoolean("toolBoxEnabled");
                    }
                }

                if (userInfo.hasKey("toolBoxAlwaysVisible")) {
                    if (userInfo.getBoolean("toolBoxAlwaysVisible") || (!userInfo.getBoolean("toolBoxAlwaysVisible"))) {
                        toolBoxAlwaysVisible = userInfo.getBoolean("toolBoxAlwaysVisible");
                    }
                }

                if (userInfo.hasKey("meetingPasswordEnabled")) {
                    if (userInfo.getBoolean("meetingPasswordEnabled") || (!userInfo.getBoolean("meetingPasswordEnabled"))) {
                        meetingPasswordEnabled = userInfo.getBoolean("meetingPasswordEnabled");
                    }
                }

                if (userInfo.hasKey("pipModeEnabled")) {
                    if (userInfo.getBoolean("pipModeEnabled") || (!userInfo.getBoolean("pipModeEnabled"))) {
                        pipModeEnabled = userInfo.getBoolean("pipModeEnabled");
                    }
                }

                // check for user info
                if (userInfo.hasKey("userInfo")) {
                    ReadableMap user = userInfo.getMap("userInfo");
                    if (user.hasKey("displayName")) {
                        _userInfo.setDisplayName(user.getString("displayName"));
                    }
                    if (user.hasKey("email")) {
                        _userInfo.setEmail(user.getString("email"));
                    }
                    if (user.hasKey("avatar")) {
                        String avatarURL = user.getString("avatar");
                        try {
                            _userInfo.setAvatar(new URL(avatarURL));
                        } catch (MalformedURLException e) {
                        }
                    }
                }
            }

            // build with options
            RNMeetHourConferenceOptions meetHourOptions
                    = new RNMeetHourConferenceOptions.Builder()
                    .setServerURL(serverUrl)
                    .setRoom(roomName)
                    .setSubject(subject)
                    .setUserInfo(_userInfo)
                    .setFeatureFlag("chat.enabled", chatEnabled)
                    .setFeatureFlag("add-people.enabled", addPeopleEnabled)
                    .setFeatureFlag("invite.enabled", inviteEnabled)
                    .setFeatureFlag("meeting-name.enabled", meetingNameEnabled)
                    .setFeatureFlag("conference-timer.enabled", conferenceTimerEnabled)
                    .setFeatureFlag("pip.enabled", pipModeEnabled)
                    .setFeatureFlag("help.enabled", false)
                    .setFeatureFlag("raise-hand.enabled", raiseHandEnabled)
                    .setFeatureFlag("recording.enabled", recordingEnabled)
                    .setFeatureFlag("live-streaming.enabled", liveStreamEnabled)
                    .setFeatureFlag("toolbox.enabled", toolBoxEnabled)
                    .setFeatureFlag("toolbox.alwaysVisible", toolBoxAlwaysVisible)
                    .setFeatureFlag("meeting-password.enabled", meetingPasswordEnabled)
                    // Settings for audio and video
                    .setAudioMuted(audioMuted)
                    .setVideoMuted(videoMuted)
                    .build();
            mMeetHourViewReference.getMeetHourView().join(meetHourOptions);
        });
    }

    @ReactMethod
    public void call(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMeetHourViewReference.getMeetHourView() != null) {
                    RNMeetHourUserInfo _userInfo = new RNMeetHourUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNMeetHourConferenceOptions options = new RNMeetHourConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(false)
                            .setUserInfo(_userInfo)
                            .build();
                    mMeetHourViewReference.getMeetHourView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void audioCall(String url, ReadableMap userInfo) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMeetHourViewReference.getMeetHourView() != null) {
                    RNMeetHourUserInfo _userInfo = new RNMeetHourUserInfo();
                    if (userInfo != null) {
                        if (userInfo.hasKey("displayName")) {
                            _userInfo.setDisplayName(userInfo.getString("displayName"));
                          }
                          if (userInfo.hasKey("email")) {
                            _userInfo.setEmail(userInfo.getString("email"));
                          }
                          if (userInfo.hasKey("avatar")) {
                            String avatarURL = userInfo.getString("avatar");
                            try {
                                _userInfo.setAvatar(new URL(avatarURL));
                            } catch (MalformedURLException e) {
                            }
                          }
                    }
                    RNMeetHourConferenceOptions options = new RNMeetHourConferenceOptions.Builder()
                            .setRoom(url)
                            .setAudioOnly(true)
                            .setUserInfo(_userInfo)
                            .build();
                    mMeetHourViewReference.getMeetHourView().join(options);
                }
            }
        });
    }

    @ReactMethod
    public void endCall() {
        UiThreadUtil.runOnUiThread(() -> {
            if (mMeetHourViewReference.getMeetHourView() != null) {
                mMeetHourViewReference.getMeetHourView().leave();
            }
        });
    }
}

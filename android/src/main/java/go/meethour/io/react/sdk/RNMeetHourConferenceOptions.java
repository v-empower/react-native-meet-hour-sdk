package go.meethour.io.react.sdk;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class RNMeetHourConferenceOptions implements Parcelable {
  private URL serverURL;
  private String room;
  private String token;
  private String pcode;
  private Bundle config;
  private Bundle featureFlags;
  private RNMeetHourUserInfo userInfo;

  public URL getServerURL() {
    return serverURL;
  }

  public String getRoom() {
    return room;
  }

  public String getToken() {
    return token;
  }

  public String getPcode() {
    return pcode;
  }

  public Bundle getFeatureFlags() {
    return featureFlags;
  }

  public RNMeetHourUserInfo getUserInfo() {
    return userInfo;
  }

  public static class Builder {
    private URL serverURL;
    private String room;
    private String token;
    private String pcode;

    private Bundle config;
    private Bundle featureFlags;

    private RNMeetHourUserInfo userInfo;

    public Builder() {
      config = new Bundle();
      featureFlags = new Bundle();
    }

    public Builder setServerURL(URL url) {
      this.serverURL = url;

      return this;
    }

    public Builder setRoom(String room) {
      this.room = room;

      return this;
    }

    public Builder setSubject(String subject) {
      setConfigOverride("subject", subject);

      return this;
    }

    public Builder setToken(String token) {
      this.token = token;

      return this;
    }

    public Builder setPcode(String pcode) {
      this.pcode = pcode;

      return this;
    }

    public Builder setAudioMuted(boolean audioMuted) {
      setConfigOverride("startWithAudioMuted", audioMuted);

      return this;
    }

    public Builder setAudioOnly(boolean audioOnly) {
      setConfigOverride("startAudioOnly", audioOnly);

      return this;
    }

    public Builder setVideoMuted(boolean videoMuted) {
      setConfigOverride("startWithVideoMuted", videoMuted);

      return this;
    }

    public Builder setFeatureFlag(String flag, boolean value) {
      this.featureFlags.putBoolean(flag, value);

      return this;
    }

    public Builder setFeatureFlag(String flag, String value) {
      this.featureFlags.putString(flag, value);

      return this;
    }

    public Builder setFeatureFlag(String flag, int value) {
      this.featureFlags.putInt(flag, value);

      return this;
    }

    public Builder setUserInfo(RNMeetHourUserInfo userInfo) {
      this.userInfo = userInfo;

      return this;
    }

    public Builder setConfigOverride(String config, String value) {
      this.config.putString(config, value);

      return this;
    }

    public Builder setConfigOverride(String config, int value) {
      this.config.putInt(config, value);

      return this;
    }

    public Builder setConfigOverride(String config, boolean value) {
      this.config.putBoolean(config, value);

      return this;
    }

    public Builder setConfigOverride(String config, Bundle bundle) {
      this.config.putBundle(config, bundle);

      return this;
    }

    public Builder setConfigOverride(String config, String[] list) {
      this.config.putStringArray(config, list);

      return this;
    }

    public RNMeetHourConferenceOptions build() {
      RNMeetHourConferenceOptions options = new RNMeetHourConferenceOptions();

      options.serverURL = this.serverURL;
      options.room = this.room;
      options.token = this.token;
      options.pcode = this.pcode;
      options.config = this.config;
      options.featureFlags = this.featureFlags;
      options.userInfo = this.userInfo;

      return options;
    }
  }

  private RNMeetHourConferenceOptions() {
  }

  private RNMeetHourConferenceOptions(Parcel in) {
    serverURL = (URL) in.readSerializable();
    room = in.readString();
    token = in.readString();
    pcode = in.readString();
    config = in.readBundle();
    featureFlags = in.readBundle();
    userInfo = new RNMeetHourUserInfo(in.readBundle());
  }

  Bundle asProps() {
    Bundle props = new Bundle();

    if (!featureFlags.containsKey("pip.enabled")) {
      featureFlags.putBoolean("pip.enabled", true);
    }

    props.putBundle("flags", featureFlags);

    Bundle urlProps = new Bundle();

    if (room != null && room.contains("://")) {
      urlProps.putString("url", room);
    } else {
      if (serverURL != null) {
        urlProps.putString("serverURL", serverURL.toString());
      }
      if (room != null) {
        urlProps.putString("room", room);
      }
    }

    if (token != null) {
      urlProps.putString("jwt", token);
    }

    if (pcode != null) {
      urlProps.putString("pcode", pcode);
    }

    if (userInfo != null) {
      props.putBundle("userInfo", userInfo.asBundle());
    }

    urlProps.putBundle("config", config);
    props.putBundle("url", urlProps);

    return props;
  }

  public static final Creator<RNMeetHourConferenceOptions> CREATOR = new Creator<RNMeetHourConferenceOptions>() {
    @Override
    public RNMeetHourConferenceOptions createFromParcel(Parcel in) {
      return new RNMeetHourConferenceOptions(in);
    }

    @Override
    public RNMeetHourConferenceOptions[] newArray(int size) {
      return new RNMeetHourConferenceOptions[size];
    }
  };

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeSerializable(serverURL);
    dest.writeString(room);
    dest.writeString(token);
    dest.writeString(pcode);
    dest.writeBundle(config);
    dest.writeBundle(featureFlags);
    dest.writeBundle(userInfo != null ? userInfo.asBundle() : new Bundle());
  }

  @Override
  public int describeContents() {
    return 0;
  }
}

package go.meethour.io.react.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import go.meethour.io.MeetHourSDK.android.BroadcastEvent;
import go.meethour.io.MeetHourSDK.android.MeetHourActivity;
import go.meethour.io.MeetHourSDK.android.MeetHourConferenceOptions;
import go.meethour.io.MeetHourSDK.android.MeetHourView;

public class MeetHourActivityExtended extends MeetHourActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onUserLeaveHint() {
    handlePictureInPicture();
  }

  public static void launchExtended(Context context, MeetHourConferenceOptions options) {
    Intent intent = new Intent(context, MeetHourActivityExtended.class);

    intent.setAction("go.meethour.io.CONFERENCE");
    intent.putExtra("MeetHourConferenceOptions", options);

    if (!(context instanceof Activity)) {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    context.startActivity(intent);
  }

  private void handlePictureInPicture() {
    MeetHourConferenceOptions conferenceOptions = getIntent().getParcelableExtra("MeetHourConferenceOptions");

    if (conferenceOptions != null) {
      Bundle flags = conferenceOptions.getFeatureFlags();

      if (flags != null) {
        if (flags.getBoolean("pip.enabled")) {
          MeetHourView view = this.getMeetHourView();

          if (view != null) {
            view.enterPictureInPicture();
          }
        }
      }
    }
  }
}

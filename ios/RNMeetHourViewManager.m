#import "RNMeetHourViewManager.h"
#import "RNMeetHourView.h"
#import <MeetHourSDK/MeetHourUserInfo.h>

@implementation RNMeetHourViewManager{
    RNMeetHourView *meetHourView;
}

RCT_EXPORT_MODULE(RNMeetHourView)
RCT_EXPORT_VIEW_PROPERTY(onConferenceJoined, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onConferenceTerminated, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onConferenceWillJoin, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onEnteredPip, RCTBubblingEventBlock)

- (UIView *)view
{
  meetHourView = [[RNMeetHourView alloc] init];
  meetHourView.delegate = self;
  return meetHourView;
}

RCT_EXPORT_METHOD(initialize)
{
    RCTLogInfo(@"Initialize is deprecated in v2");
}

RCT_EXPORT_METHOD(join_meethour:(NSDictionary *)userInfo)
{
    RCTLogInfo(@"Load UserInfo %@", userInfo);

    NSString *roomName = @"";
    NSString *subject = @"";
    NSString *serverURL = @"";

    BOOL audioMuted = false;
    BOOL videoMuted = false;
    BOOL chatEnabled = true;
    BOOL addPeopleEnabled = true;
    BOOL inviteEnabled = true;
    BOOL meetingNameEnabled = true;
    BOOL conferenceTimerEnabled = true;
    BOOL raiseHandEnabled = false;
    BOOL recordingEnabled = false;
    BOOL liveStreamEnabled = false;
    BOOL toolBoxEnabled = true;
    BOOL toolBoxAlwaysVisible = true;
    BOOL meetingPasswordEnabled = true;
    BOOL pipModeEnabled = true;


    MeetHourUserInfo * _userInfo = [[MeetHourUserInfo alloc] init];
    if (userInfo != NULL) {
      if (userInfo[@"displayName"] != NULL) {
        _userInfo.displayName = userInfo[@"displayName"];
      }
      if (userInfo[@"email"] != NULL) {
        _userInfo.email = userInfo[@"email"];
      }
      if (userInfo[@"avatar"] != NULL) {
        NSURL *url = [NSURL URLWithString:[userInfo[@"avatar"] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
      }
      if (userInfo[@"roomName"] != NULL){
        roomName = userInfo[@"roomName"];
      }
      if (userInfo[@"subject"] != NULL){
        subject = userInfo[@"subject"];
      }
      if (userInfo[@"audioMuted"] != NULL){
        audioMuted = userInfo[@"audioMuted"];
      }
      if (userInfo[@"videoMuted"] != NULL){
        videoMuted = userInfo[@"videoMuted"];
      }
      if (userInfo[@"serverUrl"] != NULL){
        serverURL = userInfo[@"serverUrl"];
      }
      if (userInfo[@"chatEnabled"] != NULL){
        chatEnabled = userInfo[@"chatEnabled"];
      }
      if (userInfo[@"addPeopleEnabled"]) {
        addPeopleEnabled = userInfo[@"addPeopleEnabled"];
      }
      if (userInfo[@"inviteEnabled"]) {
        inviteEnabled = userInfo[@"inviteEnabled"];
      }
      if (userInfo[@"meetingNameEnabled"]) {
        meetingNameEnabled = userInfo[@"meetingNameEnabled"];
      }
      if (userInfo[@"conferenceTimerEnabled"]) {
        conferenceTimerEnabled = userInfo[@"conferenceTimerEnabled"];
      }
      if (userInfo[@"raiseHandEnabled"]) {
        raiseHandEnabled = userInfo[@"raiseHandEnabled"];
      }
      if (userInfo[@"recordingEnabled"]) {
        recordingEnabled = userInfo[@"recordingEnabled"];
      }
      if (userInfo[@"liveStreamEnabled"]) {
        liveStreamEnabled = userInfo[@"liveStreamEnabled"];
      }
      if (userInfo[@"toolBoxEnabled"]) {
        toolBoxEnabled = userInfo[@"toolBoxEnabled"];
      }
      if (userInfo[@"toolBoxAlwaysVisible"]) {
        toolBoxAlwaysVisible = userInfo[@"toolBoxAlwaysVisible"];
      }
      if (userInfo[@"meetingPasswordEnabled"]) {
        meetingPasswordEnabled = userInfo[@"meetingPasswordEnabled"];
      }
      if (userInfo[@"pipModeEnabled"]) {
        pipModeEnabled = userInfo[@"pipModeEnabled"];
      }
    }
    dispatch_sync(dispatch_get_main_queue(), ^{
      MeetHourConferenceOptions *options = [MeetHourConferenceOptions fromBuilder:^(MeetHourConferenceOptionsBuilder *builder) {
        builder.serverURL = [NSURL URLWithString:serverURL];
        builder.room = roomName;
        builder.subject = subject;
        builder.userInfo = _userInfo;
        builder.audioMuted = audioMuted;
        builder.videoMuted = videoMuted;
        [builder setFeatureFlag:@"chat.enabled" withBoolean:chatEnabled];
        [builder setFeatureFlag:@"add-people.enabled" withBoolean:addPeopleEnabled];
        [builder setFeatureFlag:@"invite.enabled" withBoolean:inviteEnabled];
        [builder setFeatureFlag:@"meeting-name.enabled" withBoolean:meetingNameEnabled];
        [builder setFeatureFlag:@"conference-timer.enabled" withBoolean:conferenceTimerEnabled];
        [builder setFeatureFlag:@"pip.enabled" withBoolean:pipModeEnabled];
        [builder setFeatureFlag:@"help.enabled" withBoolean:false];
        [builder setFeatureFlag:@"raise-hand.enabled" withBoolean:raiseHandEnabled];
        [builder setFeatureFlag:@"ios.recording.enabled" withBoolean:recordingEnabled];
        [builder setFeatureFlag:@"live-streaming.enabled" withBoolean:liveStreamEnabled];
        [builder setFeatureFlag:@"toolbox.enabled" withBoolean:toolBoxEnabled];
        [builder setFeatureFlag:@"toolbox.alwaysVisible" withBoolean:toolBoxAlwaysVisible];
        [builder setFeatureFlag:@"meeting-password.enabled" withBoolean:meetingNameEnabled];
      }];
      [meetHourView join:options];
    });
}

RCT_EXPORT_METHOD(call:(NSString *)urlString userInfo:(NSDictionary *)userInfo)
{
    RCTLogInfo(@"Load URL %@", urlString);
    MeetHourUserInfo * _userInfo = [[MeetHourUserInfo alloc] init];
    if (userInfo != NULL) {
      if (userInfo[@"displayName"] != NULL) {
        _userInfo.displayName = userInfo[@"displayName"];
      }
      if (userInfo[@"email"] != NULL) {
        _userInfo.email = userInfo[@"email"];
      }
      if (userInfo[@"avatar"] != NULL) {
        NSURL *url = [NSURL URLWithString:[userInfo[@"avatar"] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
      }
    }
    dispatch_sync(dispatch_get_main_queue(), ^{
        MeetHourConferenceOptions *options = [MeetHourConferenceOptions fromBuilder:^(MeetHourConferenceOptionsBuilder *builder) {        
            builder.room = urlString;
            builder.userInfo = _userInfo;
        }];
        [meetHourView join:options];
    });
}

RCT_EXPORT_METHOD(audioCall:(NSString *)urlString userInfo:(NSDictionary *)userInfo)
{
    RCTLogInfo(@"Load Audio only URL %@", urlString);
    MeetHourUserInfo * _userInfo = [[MeetHourUserInfo alloc] init];
    if (userInfo != NULL) {
      if (userInfo[@"displayName"] != NULL) {
        _userInfo.displayName = userInfo[@"displayName"];
      }
      if (userInfo[@"email"] != NULL) {
        _userInfo.email = userInfo[@"email"];
      }
      if (userInfo[@"avatar"] != NULL) {
        NSURL *url = [NSURL URLWithString:[userInfo[@"avatar"] stringByAddingPercentEncodingWithAllowedCharacters:[NSCharacterSet URLQueryAllowedCharacterSet]]];
        _userInfo.avatar = url;
      }
    }
    dispatch_sync(dispatch_get_main_queue(), ^{
        MeetHourConferenceOptions *options = [MeetHourConferenceOptions fromBuilder:^(MeetHourConferenceOptionsBuilder *builder) {        
            builder.room = urlString;
            builder.userInfo = _userInfo;
            builder.audioOnly = YES;
        }];
        [meetHourView join:options];
    });
}

RCT_EXPORT_METHOD(endCall)
{
    dispatch_sync(dispatch_get_main_queue(), ^{
        [meetHourView leave];
    });
}

#pragma mark MeetHourViewDelegate

- (void)conferenceJoined:(NSDictionary *)data {
    RCTLogInfo(@"Conference joined");
    if (!meetHourView.onConferenceJoined) {
        return;
    }

    meetHourView.onConferenceJoined(data);
}

- (void)conferenceTerminated:(NSDictionary *)data {
    RCTLogInfo(@"Conference terminated");
    if (!meetHourView.onConferenceTerminated) {
        return;
    }

    meetHourView.onConferenceTerminated(data);
}

- (void)conferenceWillJoin:(NSDictionary *)data {
    RCTLogInfo(@"Conference will join");
    if (!meetHourView.onConferenceWillJoin) {
        return;
    }

    meetHourView.onConferenceWillJoin(data);
}

- (void)enterPictureInPicture:(NSDictionary *)data {
    RCTLogInfo(@"Enter Picture in Picture");
    if (!meetHourView.onEnteredPip) {
        return;
    }

    meetHourView.onEnteredPip(data);
}

@end

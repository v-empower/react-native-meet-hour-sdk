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

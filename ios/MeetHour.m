#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(MeetHour, NSObject)
    RCT_EXTERN_METHOD(hangUp)
    RCT_EXTERN_METHOD(launchMeetHourView:(NSDictionary)options
                      resolver:(RCTPromiseResolveBlock)resolve
                      rejecter:(RCTPromiseRejectBlock)reject)
    RCT_EXTERN_METHOD(launch:(NSDictionary)options
                      resolver:(RCTPromiseResolveBlock)resolve
                      rejecter:(RCTPromiseRejectBlock)reject)
@end

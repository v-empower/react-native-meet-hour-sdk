#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(, NSObject)
RCT_EXTERN_METHOD(hangUp)
RCT_EXTERN_METHOD(launchView:(NSDictionary)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
RCT_EXTERN_METHOD(launch:(NSDictionary)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
@end

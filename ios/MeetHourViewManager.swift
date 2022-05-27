import UIKit
import React
import MeetHourSDK

@objc(MeetHourViewManager)
class MeetHourViewManager: RCTViewManager {
  override func view() -> UIView! {
    return RNMeetHourView()
  }
  
  override class func requiresMainQueueSetup() -> Bool {
    return true
  }
}

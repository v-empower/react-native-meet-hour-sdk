import UIKit
import MeetHourSDK

@objc(MeetHour)
class MeetHour: NSObject {
    
  var vc: MeetHourViewController?
    
  @objc func hangUp() {
     self.vc?.mHView.hangUp()
  }
    
  @objc func launchMeetHourView(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    DispatchQueue.main.async {
      let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
      let _vc = MeetHourViewController()
      
      _vc.resolver = resolve
      _vc.modalPresentationStyle = .fullScreen
      _vc.conferenceOptions = MeetHourUtil.buildConferenceOptions(options)
                
      rootViewController.pushViewController(_vc, animated: false)
        
        self.vc = _vc
    }
  }

  @objc func launch(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    launchMeetHourView(options, resolver: resolve, rejecter: reject)
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

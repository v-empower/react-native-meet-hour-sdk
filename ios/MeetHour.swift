import UIKit
import MeetHourSDK

@objc(MeetHour)
class MeetHour: NSObject {
    
    var vc: MeetHourViewController?
    
    @objc func hangUp() {
        self.vc?.mHView.hangUp()
    }
    
    @objc func launchMeetHourView(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) {
        DispatchQueue.main.async {
            // Check if the current `MeetHourViewController` is already presented or being presented
            if let currentVC = self.vc, currentVC.isBeingPresented || currentVC.isViewLoaded {
                print("MeetHourViewController is already presented or being presented.")
                reject("launch_error", "MeetHourViewController is already presented or being presented.", nil)
                return
            }

            // Create a new MeetHourViewController instance
            let _vc = MeetHourViewController()
            _vc.resolver = resolve
            _vc.modalPresentationStyle = .fullScreen
            _vc.conferenceOptions = MeetHourUtil.buildConferenceOptions(options)
            
            // Safely get the rootViewController
            if let rootViewController = UIApplication.shared.delegate?.window??.rootViewController {
                if let navigationController = rootViewController as? UINavigationController {
                    // If it's a UINavigationController, push the view controller
                    navigationController.pushViewController(_vc, animated: false)
                } else {
                    // If it's not a UINavigationController, present the view controller modally
                    let newNavigationController = UINavigationController(rootViewController: _vc)
                    newNavigationController.modalPresentationStyle = .fullScreen

                    // Present the new navigation controller modally
                    if !rootViewController.isBeingPresented && rootViewController.presentedViewController == nil {
                        rootViewController.present(newNavigationController, animated: true, completion: nil)
                    } else {
                        print("Error: Another view controller is already being presented.")
                        reject("launch_error", "Another view controller is already being presented.", nil)
                    }
                }
                
                self.vc = _vc
            } else {
                print("Error: rootViewController is nil")
                reject("launch_error", "Root view controller is nil", nil)
            }
        }
    }

    @objc func launch(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: @escaping RCTPromiseRejectBlock) {
        launchMeetHourView(options, resolver: resolve, rejecter: reject)
    }
    
    @objc
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
}

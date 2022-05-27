import UIKit
import MeetHourSDK

class MeetHourViewController: UIViewController {
  var conferenceOptions: MeetHourConferenceOptions?
  var resolver: RCTPromiseResolveBlock?
  var mHView = MeetHourView()

  override func viewDidLoad() {
    
    mHView.join(conferenceOptions)
    mHView.delegate = self
    
    view = mHView
  }
}

extension MeetHourViewController: MeetHourViewDelegate {
  func ready(toClose data: [AnyHashable : Any]!) {
    DispatchQueue.main.async {
        let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
        rootViewController.popViewController(animated: false)
    }
      
    if ((resolver) != nil) {
      resolver!([])
    }
  }
}

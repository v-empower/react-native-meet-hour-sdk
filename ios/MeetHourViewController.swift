import UIKit
import MeetHourSDK

class MeetHourViewController: UIViewController {
  var conferenceOptions: MeetHourConferenceOptions?
  var resolver: RCTPromiseResolveBlock?
  var mHView = MeetHourView()
  fileprivate var pipViewCoordinator: PiPViewCoordinator?

  override func viewDidLoad() {
    
    mHView.join(conferenceOptions)
    mHView.delegate = self
    
    view = mHView

    // Enable meet hour view to be a view that can be displayed
    // on top of all the things, and let the coordinator to manage
    // the view state and interactions
    pipViewCoordinator = PiPViewCoordinator(withView: mHView)
    pipViewCoordinator?.configureAsStickyView(withParentView: view)

    // animate in
    mHView.alpha = 0
    pipViewCoordinator?.show()
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

  func conferenceTerminated(_ data: [AnyHashable : Any]!) {
      DispatchQueue.main.async {
          self.pipViewCoordinator?.hide()
      }
  }

  @objc func enterPictureInPicture() {
    DispatchQueue.main.async {
        self.pipViewCoordinator?.show()
        self.pipViewCoordinator?.enterPictureInPicture()
    }
  }
}

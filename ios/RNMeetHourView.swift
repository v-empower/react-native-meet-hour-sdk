import UIKit
import MeetHourSDK
import React

class RNMeetHourView: MeetHourView {
  @objc var options: NSDictionary? {
    willSet {
      if let newOptions = newValue {
        joinCall(newOptions)
      }
    }
  }
  
  @objc var onConferenceTerminated: RCTDirectEventBlock?
  @objc var onConferenceJoined: RCTDirectEventBlock?
  @objc var onConferenceWillJoin: RCTDirectEventBlock?
  
  override init(frame: CGRect) {
    super.init(frame: frame)
    
    delegate = self
  }
  
  required init?(coder: NSCoder) {
    super.init(coder: coder)
  }
  
  func joinCall(_ options: NSDictionary) {
    DispatchQueue.main.async {
      self.join(MeetHourUtil.buildConferenceOptions(options))
    }
  }
  
  func leaveCall() {
    leave()
    hangUp()
  }
  
  override func removeFromSuperview() {
    leaveCall()
    
    super.removeFromSuperview()
  }
}

extension RNMeetHourView: MeetHourViewDelegate {
  // The SDK only turns the `pip.enabled` feature flag on by default when the
  // host's delegate responds to `enterPictureInPicture:` (see MeetHourView.m).
  // Picture-in-Picture itself is driven natively inside the SDK by
  // PictureInPictureModule/AVPictureInPictureController and never calls back
  // here, so this stays an empty stub - its only job is to answer that check,
  // which is what makes the in-conference PiP control work when the SDK is
  // embedded rather than run standalone.
  //
  // Note the Swift name: the importer splits `enterPictureInPicture:` into
  // `enterPicture(inPicture:)`, the same way `readyToClose:` becomes
  // `ready(toClose:)`. Spelling it `enterPictureInPicture` hits an obsoleted
  // Swift 3 alias and fails to build; declaring it with no argument at all
  // compiles but satisfies nothing, which is how the flag came to be off in
  // the first place. The selector is pinned too, so `respondsToSelector:`
  // cannot drift again.
  @objc(enterPictureInPicture:)
  func enterPicture(inPicture data: [AnyHashable : Any]!) {
    // Intentionally empty - see above.
  }

  func conferenceTerminated(_ data: [AnyHashable : Any]!) {
    onConferenceTerminated?(data)
  }
  
  func conferenceJoined(_ data: [AnyHashable : Any]!) {
    onConferenceJoined?(data)
  }
  
  func conferenceWillJoin(_ data: [AnyHashable : Any]!) {
    onConferenceWillJoin?(data)
  }
}

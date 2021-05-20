/**
 * @providesModule Meet Hour
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const MeetHourView = requireNativeComponent('RNMeetHourView');
export const MeetHourModule = NativeModules.RNMeetHourView;
const call = MeetHourModule.call;
const audioCall = MeetHourModule.audioCall;
MeetHourModule.call = (url, userInfo) => {
  userInfo = userInfo || {};
  call(url, userInfo);
}
MeetHourModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
}
export default MeetHourModule;



/**
 * @providesModule Meet Hour
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const MeetHourView = requireNativeComponent('RNMeetHourView');
export const MeetHourModule = NativeModules.RNMeetHourView;
const call = MeetHourModule.call;
const audioCall = MeetHourModule.audioCall;
const join_meethour = MeetHourModule.join_meethour;
MeetHourModule.call = (url, userInfo) => {
  userInfo = userInfo || {};
  call(url, userInfo);
}
MeetHourModule.audioCall = (url, userInfo) => {
  userInfo = userInfo || {};
  audioCall(url, userInfo);
}
MeetHourModule.join_meethour = (userInfo) => {
  userInfo = userInfo || {};
  join_meethour(userInfo);
}
export default MeetHourModule;

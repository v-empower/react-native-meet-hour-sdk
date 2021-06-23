/**
 * @providesModule MeetHour
 */

import { NativeModules, requireNativeComponent } from 'react-native';

export const MeetHourView = requireNativeComponent('RNMeetHourView');
export const MeetHourModule = NativeModules.RNMeetHourModule
const call = MeetHourModule.call;
MeetHourModule.call = (userInfo) => {
  userInfo = userInfo || {};
  call(userInfo);
}
export default MeetHourModule;

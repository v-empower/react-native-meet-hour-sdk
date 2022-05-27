import { NativeModules, requireNativeComponent } from 'react-native';
import type { MeetHourType, MeetHourViewType } from './types';

const { MeetHour } = NativeModules;

const MeetHourView: MeetHourViewType = requireNativeComponent('MeetHourView');

export { MeetHourView };

export default MeetHour as MeetHourType;

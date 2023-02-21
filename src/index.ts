import { NativeModules, requireNativeComponent } from 'react-native';
import ApiServices from './services/apiServices';
import type { MeetHourType, MeetHourViewType } from './types';

const { MeetHour } = NativeModules;

const MeetHourView: MeetHourViewType = requireNativeComponent('MeetHourView');

export { MeetHourView };

export default MeetHour as MeetHourType;
export { ApiServices };

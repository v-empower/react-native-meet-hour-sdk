export interface UserObjectType {
  email?: string;
  first_name?: string;
  last_name?: string;
}

export default interface ScheduleMeetingType {
  agenda?: string;
  attend?:
    | Array<number>
    | Array<UserObjectType>
    | Array<UserObjectType | number>;
  default_recording_storage?: string;
  duration_hr?: number;
  duration_min?: number;
  enable_pre_registration?: number;
  endBy?: string;
  end_date_time?: string;
  end_times?: number;
  groups?: Array<number | string>;
  hostusers?:
    | Array<number>
    | Array<UserObjectType>
    | Array<UserObjectType | number>;
  instructions?: string;
  is_recurring?: number;
  is_show_portal?: number;
  meeting_agenda?: string;
  meeting_date: string;
  meeting_meridiem: string;
  meeting_name: string;
  meeting_time: string;
  meeting_topic?: string;
  monthlyBy?: string;
  monthlyByDay?: string;
  monthlyByWeekday?: string;
  monthlyByWeekdayIndex?: string;
  options?: Array<string>;
  passcode: string;
  recurring_type?: string;
  repeat_interval?: number;
  send_calendar_invite?: number;
  timezone: string;
  weeklyWeekDays?: number;
}

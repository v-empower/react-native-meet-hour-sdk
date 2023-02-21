interface UserObjectType {
  email?: string;
  first_name?: string;
  last_name?: string;
}

export default interface EditMeeting {
  agenda?: string;
  attend?:
    | Array<number>
    | Array<UserObjectType>
    | Array<number & UserObjectType>;
  duration_hr?: number;
  duration_min?: number;
  enable_pre_registration?: number;
  endBy?: string;
  end_date_time?: string;
  groups?: Array<number>;
  hostusers?:
    | Array<number>
    | Array<UserObjectType>
    | Array<number & UserObjectType>;
  instructions?: string;
  is_recurring?: number;
  is_show_portal?: number;
  meeting_agenda?: string;
  meeting_date?: string;
  meeting_id: string;
  meeting_meridiem?: string;
  meeting_name?: string;
  meeting_time?: string;
  meeting_topic?: string;
  old_attend?:
    | Array<number>
    | Array<UserObjectType>
    | Array<number & UserObjectType>;
  options?: Array<string>;
  passcode?: string;
  recurring_type?: string;
  repeat_interval?: number;
  timezone?: string;
}

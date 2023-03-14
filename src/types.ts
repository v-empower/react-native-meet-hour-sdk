import type { ComponentType } from 'react';
import type { StyleProp, ViewStyle } from 'react-native';

export interface MeetHourUserInfo {
  displayName?: string;
  email?: string;
  avatar?: string;
}

export interface MeetHourConferenceOptions {
  room: string;
  serverUrl?: string;
  userInfo?: MeetHourUserInfo;
  token?: string;
  pcode?: string;
  subject?: string;
  audioOnly?: boolean;
  audioMuted?: boolean;
  prejoinPageEnabled?: boolean;
  videoMuted?: boolean;
  disableInviteFunctions?: boolean;
  featureFlags?: { [key: string]: boolean };
}

interface MeetHourEvent {
  nativeEvent: {
    error?: string;
    url?: string;
  };
}

export interface MeetHourViewProps {
  options: MeetHourConferenceOptions;
  style?: StyleProp<ViewStyle>;
  onConferenceTerminated?: (e: MeetHourEvent) => void;
  onConferenceJoined?: (e: MeetHourEvent) => void;
  onConferenceWillJoin?: (e: MeetHourEvent) => void;
}

export interface MeetHourType {
  launchMeetHourView: (options: MeetHourConferenceOptions) => Promise<void>;
  hangUp: () => void;
}

export type MeetHourViewType = ComponentType<MeetHourViewProps>;

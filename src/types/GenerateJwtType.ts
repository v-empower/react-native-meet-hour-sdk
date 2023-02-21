export default interface GenerateJwtType {
  config?: {
    disableDeepLinking?: string;
    dropboxappKey?: string;
    dropboxredirectURI?: string;
    enableClosePage?: string;
    enableWelcomePage?: string;
    fileRecordingsEnabled?: boolean;
    liveStreamingEnabled?: boolean;
    p2penabled?: boolean;
    requireDisplayName?: string;
    resolution?: number;
    startAudioMuted?: number;
    videoheightideal?: number;
    videoheightmax?: number;
    videoheightmin?: number;
    videowidthideal?: number;
    videowidthmax?: number;
    videowidthmin?: number;
  };
  contact_id?: number;
  meeting_id: string;
  ui_config?: {
    ANDROID_APP_PACKAGE?: string;
    APP_NAME?: string;
    APP_SCHEME?: string;
    BRAND_WATERMARK_BACKGROUND?: string;
    DEFAULT_LOGO_URL?: string;
    ENABLE_MOBILE_BROWSER?: string;
    HIDE_DEEP_LINKING_LOGO?: string;
    MEET_HOUR_WATERMARK_LINK?: string;
    MOBILE_APP_PROMO?: string;
    MOBILE_DOWNLOAD_LINK_ANDROID?: string;
    MOBILE_DOWNLOAD_LINK_IOS?: string;
    NATIVE_APP_NAME?: string;
    PROVIDER_NAME?: string;
    SHOW_MEET_HOUR_WATERMARK?: string;
    disablePrejoinFooter?: string;
    disablePrejoinHeader?: string;
    toolbar_buttons?: Array<string>;
  };
}

import axios from 'axios';

import { API_BASE_URL, API_VERSION, GRANT_TYPE } from '../constants';
import type AddContactType from '../types/AddContactType';
import type ContactsType from '../types/ContactsType';
import type EditContactType from '../types/EditContactType';
import type EditMeetingType from '../types/EditMeetingType';
import type GenerateJwtType from '../types/GenerateJwtType';
import type LoginType from '../types/LoginType';
import type RecordingsList from '../types/RecordingsList';
import type RefreshTokenType from '../types/RefreshTokenType';
import type ScheduleMeetingType from '../types/ScheduleMeetingType';

interface PathParam {
  key: string;
  value: string;
}

type ApiEndpoint =
  | 'add_contact'
  | 'login'
  | 'schedule_meeting'
  | 'get_jwt'
  | 'upcoming_meeting'
  | 'timezone'
  | 'recordings_list'
  | 'edit_meeting'
  | 'edit_contact'
  | 'view_meeting'
  | 'archive_meeting'
  | 'missed_meetings'
  | 'completed_meetings'
  | 'user_details'
  | 'refresh_token'
  | 'contacts_list';

const apiEndpointUrl = (baseUrl: string, endpoint: ApiEndpoint): string => {
  switch (endpoint) {
    case 'login':
      return `${baseUrl}/oauth/token`;
    case 'add_contact':
      return `${baseUrl}/api/${API_VERSION}/customer/addcontact`;
    case 'contacts_list':
      return `${baseUrl}/api/${API_VERSION}/customer/contacts`;
    case 'schedule_meeting':
      return `${baseUrl}/api/${API_VERSION}/meeting/schedulemeeting`;
    case 'get_jwt':
      return `${baseUrl}/api/${API_VERSION}/getjwt`;
    case 'upcoming_meeting':
      return `${baseUrl}/api/${API_VERSION}/meeting/upcomingmeetings`;
    case 'timezone':
      return `${baseUrl}/api/${API_VERSION}/getTimezone`;
    case 'recordings_list':
      return `${baseUrl}/api/${API_VERSION}/customer/videorecordinglist`;
    case 'edit_meeting':
      return `${baseUrl}/api/${API_VERSION}/meeting/editmeeting`;
    case 'edit_contact':
      return `${baseUrl}/api/${API_VERSION}/customer/editcontact`;
    case 'view_meeting':
      return `${baseUrl}/api/${API_VERSION}/meeting/viewmeeting`;
    case 'archive_meeting':
      return `${baseUrl}/api/${API_VERSION}/meeting/archivemeeting`;
    case 'missed_meetings':
      return `${baseUrl}/api/${API_VERSION}/meeting/missedmeetings`;
    case 'completed_meetings':
      return `${baseUrl}/api/${API_VERSION}/meeting/completedmeeting`;
    case 'user_details':
      return `${baseUrl}/api/${API_VERSION}/customer/user_details`;
    case 'refresh_token':
      return `${baseUrl}/oauth/token`;
  }
};

const substitutePathParameter = (
  url: string,
  pathParam: PathParam[] = []
): string => {
  const constructedUrl = url;

  pathParam.forEach((item: PathParam) => {
    url.replace(`{${item.key}}`, item.value ? item.value : '');
  });

  return constructedUrl;
};

/**
 * ApiServices Class.
 */
class ApiServices {
  private static BASE_URL = API_BASE_URL ?? 'https://api.meethour.io';

  private static getHeaders = (token: string) => {
    return {
      'content-type': 'application/json',
      'authorization': `Bearer ${token}`,
    };
  };

  /**
   * postFetch() : For making Post HTTP Methods via axios
   * @param {string} token - access token to make API calls.
   * @param {ApiEndpoint} endpoint End Point types.
   * @param {any} body API call body.
   * @param {PathParam} pathParam additional parameters to be passed to API
   * @returns {string} response data that we get from API.
   */
  private static async postFetch(
    token: string,
    endpoint: ApiEndpoint,
    body: any,
    pathParam: PathParam[] = []
  ) {
    const constructedUrl = substitutePathParameter(
      apiEndpointUrl(this.BASE_URL, endpoint),
      pathParam
    );

    try {
      const response = await axios.post(constructedUrl, JSON.stringify(body), {
        method: 'POST',
        headers: ApiServices.getHeaders(token),
      });

      if (response.status >= 400 && response.status < 600) {
        console.log('Bad response from server.', response);

        return null;
      }

      return await response.data;
    } catch (error) {
      console.log(error);
    }
  }

  /**
   * login() : To authenticate and login to get access token
   * @param {string} grant_type - Grant Type. Accepts "password"
   * @param {string} client_id Client ID -> Get it from Developers page.
   * @param {string} client_secret Client Secret -> Get it from Developers page.
   * @param {string} username Username -> Email account used to Signup for Meet Hour
   * @param {string} password Password -> Password of your Meet Hour account.
   * @returns {string} response data that we get from API.
   */
  static async login({
    grant_type = GRANT_TYPE,
    client_id,
    client_secret,
    username,
    password,
  }: LoginType): Promise<any> {
    return ApiServices.postFetch('', 'login', {
      grant_type,
      client_id,
      client_secret,
      username,
      password,
    });
  }

  /**
   * getRefreshToken() : To get new token from refresh token
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async getRefreshToken(
    token: string,
    body: RefreshTokenType
  ): Promise<any> {
    return ApiServices.postFetch(token, 'refresh_token', body);
  }

  /**
   * userDetails() : For making Post HTTP Methods via axios
   * @param {string} token - access token to make API calls.
   * @returns {string} response data that we get from API.
   */
  static async userDetails(token: string): Promise<any> {
    return ApiServices.postFetch(token, 'user_details', '');
  }

  /**
   * generateJwt() : JWT is needed to join the meeting with user information. Usually used if Moderator is joining.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async generateJwt(token: string, body: GenerateJwtType): Promise<any> {
    return ApiServices.postFetch(token, 'get_jwt', body);
  }

  /**
   * addContact() : To add contact in Meet Hour Database.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async addContact(token: string, body: AddContactType): Promise<any> {
    return ApiServices.postFetch(token, 'add_contact', body);
  }

  /**
   * timezone() : To get all the timezones used in Meet Hour while Meeting is being Scheduled.
   * @param {string} token - access token to make API calls.
   * @returns {string} response data that we get from API.
   */
  static async timezone(token: string): Promise<any> {
    return ApiServices.postFetch(token, 'timezone', {});
  }

  /**
   * contactsList() : To get all the contacts available on Meet Hour account.
   * @param {string} token - access token to make API calls.
   * @param {ContactsType} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async contactsList(token: string, body: ContactsType): Promise<any> {
    return ApiServices.postFetch(token, 'contacts_list', body);
  }

  /**
   * editContact() : To edit a specific contact.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async editContact(token: string, body: EditContactType): Promise<any> {
    return ApiServices.postFetch(token, 'edit_contact', body);
  }

  /**
   * scheduleMeeting() : Function to hit a Schedule Meeting API.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async scheduleMeeting(
    token: string,
    body: ScheduleMeetingType
  ): Promise<any> {
    return ApiServices.postFetch(token, 'schedule_meeting', body);
  }

  /**
   * upcomingMeetings() : Function to hit a Schedule Meeting API.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async upcomingMeetings(
    token: string,
    body: {
      limit: number;
      page: number;
    }
  ): Promise<any> {
    return ApiServices.postFetch(token, 'upcoming_meeting', body);
  }

  /**
   * archiveMeeting() : To get archive Meeting
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async archiveMeeting(
    token: string,
    body: {
      id?: number;
    }
  ): Promise<any> {
    return ApiServices.postFetch(token, 'archive_meeting', body);
  }

  /**
   * missedMeetings() : To get all the Missed Meeting.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async missedMeetings(
    token: string,
    body: {
      limit: number;
      page: number;
    }
  ): Promise<any> {
    return ApiServices.postFetch(token, 'missed_meetings', body);
  }

  /**
   * completedMeetings() : To get all the Completed Meetings.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async completedMeetings(
    token: string,
    body: {
      limit: number;
      page: number;
    }
  ): Promise<any> {
    return ApiServices.postFetch(token, 'completed_meetings', body);
  }

  /**
   * editMeeting() : To Edit a specific meeting.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async editMeeting(token: string, body: EditMeetingType): Promise<any> {
    return ApiServices.postFetch(token, 'edit_meeting', body);
  }

  /**
   * viewMeeting() : To get information of specific meeting.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async viewMeeting(
    token: string,
    body: { meeting_id: string }
  ): Promise<any> {
    return ApiServices.postFetch(token, 'view_meeting', body);
  }

  /**
   * recordingsList() : To get all the recording list.
   * @param {string} token - access token to make API calls.
   * @param {any} body - API call body.
   * @returns {string} response data that we get from API.
   */
  static async recordingsList(
    token: string,
    body: RecordingsList
  ): Promise<any> {
    return ApiServices.postFetch(token, 'recordings_list', body);
  }
}
export default ApiServices;

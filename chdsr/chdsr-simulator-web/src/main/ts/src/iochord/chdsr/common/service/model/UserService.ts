import { BaseService } from '../BaseService';

/**
 *
 * @package chdsr
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 */
export class UserService extends BaseService {

  public static readonly BASE_URI: string = BaseService.BASE_URI + '/user';

  public static getInstance(): UserService {
    if (UserService.__INSTANCE == null) {
      UserService.__INSTANCE = new UserService();
    }
    return UserService.__INSTANCE;
  }

  private static __INSTANCE: UserService;

}

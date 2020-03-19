/**
 * The authentication register interface.
 *
 * @export
 * @interface AuthHasRegisterInterface
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export interface AuthHasRegisterInterface {

  /**
   * Full name of the user.
   *
   * @type {string}
   * @memberof AuthHasRegisterInterface
   */
  fullName: string;

  /**
   * The handle name picked by the user.
   *
   * @type {string}
   * @memberof AuthHasRegisterInterface
   */
  username: string;

  /**
   * The email of the user.
   *
   * Format: <email_address>@<domain>
   * e.g.: yabes.wirawan@gmail.com
   *
   * @type {string}
   * @memberof AuthHasRegisterInterface
   */
  email: string;

  /**
   * The password of the user.
   *
   * @type {string}
   * @memberof AuthHasRegisterInterface
   */
  password: string;

  /**
   * Confirm password field of the user.
   *
   * @type {string}
   * @memberof AuthHasRegisterInterface
   */
  confirmPassword: string;

  /**
   * A flag indicating an error is exists. False otherwise.
   *
   * @type {boolean}
   * @memberof AuthHasLoginInterface
   */
  isError: boolean;

  /**
   * The error message when isError is assigned to TRUE.
   *
   * @type {string}
   * @memberof AuthHasLoginInterface
   */
  errorMessage: string;

  /**
   * Handler for user sign up.
   *
   * @memberof AuthHasRegisterInterface
   */
  signUp(): void;
}

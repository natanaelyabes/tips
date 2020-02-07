/**
 * The auth login interface.
 *
 * @export
 * @interface AuthHasLoginInterface
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export interface AuthHasLoginInterface {

  /**
   * The email of the user.
   *
   * Format: <email_address>@<domain>
   * e.g.: yabes.wirawan@gmail.com
   *
   * @type {string}
   * @memberof AuthHasLoginInterface
   */
  email: string;

  /**
   * Alphanumeric password of the user.
   *
   * @type {string}
   * @memberof AuthHasLoginInterface
   */
  password: string;

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
   * Handler for user login.
   *
   * @memberof AuthHasLoginInterface
   */
  login(): void;
}

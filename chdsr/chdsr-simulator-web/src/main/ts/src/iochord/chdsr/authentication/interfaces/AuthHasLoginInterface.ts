/**
 *
 *
 * @export
 * @interface AuthHasLoginInterface
 */
export interface AuthHasLoginInterface {
  email: string;
  password: string;
  isError: boolean;
  errorMessage: string;
  fn_auth_login(): void;
}

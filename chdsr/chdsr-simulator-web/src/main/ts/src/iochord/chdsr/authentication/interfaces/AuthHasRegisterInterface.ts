/**
 *
 *
 * @export
 * @interface AuthHasRegisterInterface
 */
export interface AuthHasRegisterInterface {
  fullName: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  isError: boolean;
  errorMessage: string;
  fn_auth_sign_up(): void;
}

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
  login(): void;
}

/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface AuthHasRegisterInterface {
  fullName: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  isError: boolean;
  errorMessage: string;
  signUp(): void;
}

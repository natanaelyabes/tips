/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface AuthHasLoginInterface {
  email: string;
  password: string;
  isError: boolean;
  errorMessage: string;
  login(): void;
}

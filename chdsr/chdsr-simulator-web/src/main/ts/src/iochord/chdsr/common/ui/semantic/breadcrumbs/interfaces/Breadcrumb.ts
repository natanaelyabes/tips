/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface Breadcrumb {
  url: string | undefined;
  class: string;
  divider: string;
  isRouterLink: boolean;
  title: string;
}

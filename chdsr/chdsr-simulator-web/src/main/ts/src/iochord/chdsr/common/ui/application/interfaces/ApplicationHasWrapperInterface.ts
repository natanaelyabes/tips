import { BreadcrumbHasInterface } from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/interfaces/BreadcrumbHasInterface';


/**
 *
 *
 * @export
 * @interface ApplicationHasWrapperInterface
 */
export interface ApplicationHasWrapperInterface {
  title: string;
  breadcrumbs: BreadcrumbHasInterface[];
  titleMenuBarItems: any;
  leftMenuBarItems: any;
  rightMenuBarItems: any;
  ribbonMenuItems: any;
  content: any;
  fn_application_set_title(): void;
  fn_application_set_breadcrumb(): void;
  fn_application_set_title_menu_bar(): void;
  fn_application_set_left_side_menu_bar(): void;
  fn_application_set_right_side_menu_bar(): void;
  fn_application_set_ribbon_menu_item(): void;
  fn_application_set_content(): void;
}

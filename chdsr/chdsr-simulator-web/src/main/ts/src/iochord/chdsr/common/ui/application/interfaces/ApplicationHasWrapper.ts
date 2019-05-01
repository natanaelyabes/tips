import { HasBreadcrumb } from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/interfaces/HasBreadcrumb';


/**
 *
 *
 * @export
 * @interface ApplicationHasWrapper
 */
export interface ApplicationHasWrapper {
  title: string;
  breadcrumbs: HasBreadcrumb[];
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

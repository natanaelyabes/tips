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
  setTitle(): void;
  setBreadcrumb(): void;
  setTitleMenubar(): void;
  setLeftMenuSidebar(): void;
  setRightMenuSidebar(): void;
  setRibbonMenuItem(): void;
  setContent(): void;
}

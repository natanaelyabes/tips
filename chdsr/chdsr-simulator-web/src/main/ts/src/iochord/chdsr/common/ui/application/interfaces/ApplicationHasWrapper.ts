import { Breadcrumb } from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/interfaces/Breadcrumb';


/**
 *
 *
 * @export
 * @interface ApplicationHasWrapper
 */
export interface ApplicationHasWrapper {
  title: string;
  breadcrumbs: Breadcrumb[];
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
  setApplicationWrapperProperties(): void;
}

import { Breadcrumb } from '@/iochord/chdsr/common/ui/semantic/breadcrumbs/interfaces/Breadcrumb';


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
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

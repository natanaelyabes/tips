/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface Layout01HasWrapper {
  title: string;
  breadcrumbs: any;
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
  setWrapperProperties(): void;
}

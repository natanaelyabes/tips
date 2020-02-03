export interface ExplorerLayout {
  title: string;
  breadcrumbs: any;
  titleMenuBarItems: any;
  leftMenuBarItems: any;
  depthTwoLeftMenuBarItems: any;
  content: any;
  setTitle(): void;
  setBreadcrumb(): void;
  setTitleMenubar(): void;
  setLeftMenuSidebar(): void;
  setDepthTwoLeftMenuSidebar(): void;
  setContent(): void;
  setWrapperProperties(): void;
}

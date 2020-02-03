export interface VisualizerLayout {
  title: string;
  breadcrumbs: any;
  titleMenuBarItems: any;
  settingsBarItems: any;
  content: any;
  setTitle(): void;
  setBreadcrumb(): void;
  setTitleMenubar(): void;
  setSettingsBar(): void;
  setContent(): void;
  setWrapperProperties(): void;
}

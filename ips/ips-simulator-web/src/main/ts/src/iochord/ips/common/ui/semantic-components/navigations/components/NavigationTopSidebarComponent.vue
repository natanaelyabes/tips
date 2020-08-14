<!--
  @package ts
  @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
  @since 2019
-->
<template>
  <div class="navigation-top-sidebar component">
    <div class="ui top fixed menu">
      <div class="item">
        <div v-on:click="toggleMenu()" id="menu-button" class="ui big black icon compact toggle button" data-transition="overlay">
          <i id="menu-button" class="sidebar icon"></i>
        </div>
      </div>
      <slot name="top-left-menu-item"></slot>
      <slot name="top-right-menu-item"></slot>
    </div>
    <div class="ui visible inverted left vertical sidebar menu">
      <slot name="app-logo"></slot>
      <slot name="info"></slot>
      <slot name="sidebar-menu"></slot>
    </div>
    <div class="pusher">
      <router-view/>
    </div>
  </div>
</template>

<style scoped>
/**
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 *
 */
.pusher {
  transform: translate3d(0,0,0)!important;
  padding-left: 260px;
  height: 100%;
  overflow-y: auto!important;
}

.navigation-top-sidebar .top.fixed.menu {
  padding-left: 260px;
  position: fixed;
}

.navigation-top-sidebar.component {
  height: 100%;
}

.ui.left.sidebar {
  border: 0;
}

.ui.left.sidebar, .ui.left.sidebar * {
  transition: 0.2s all cubic-bezier(0.39, 0.575, 0.565, 1);
}

.ui.vertical.sidebar {
  background: #03628c;
}

.ui.vertical.sidebar a.logo.item {
  padding: 0.3em;
  background: white;
  height: 69px;
}

#server-info {
  padding: 2em 0;
}

#server-info p {
  padding-top: 1em;
}

.ui.basic.segment {
  min-height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Interfaces
import { BrowserCanHandleBreakpoints } from '@/iochord/ips/common/browser/interfaces/BrowserCanHandleBreakpoints';

// Components
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';

// JQuery Symbol Handler
declare const $: any;
@Component

/**
 * Navigation bar component.
 *
 * @export
 * @class NavigationBarComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author Natanael Yabes Wirawan <yabes.wirawan@pusan.ac.kr>
 * @since 2019
 */
export default class NavigationTopSidebarComponent extends SemanticComponent {

  /**
   * Boolean variable to open or close the menu side bar.
   *
   * @private
   * @type {boolean}
   * @memberof NavigationTopSidebarComponent
   */
  private menuIsOpen: boolean = false;

  /**
   * Mounted Vue lifecycle.
   *
   * @memberof NavigationTopSidebarComponent
   */
  public mounted(): void {
    $(document).click((e: MouseEvent) => {
      const target = e.target as HTMLElement;
      const id = target.id;
      const browser: BrowserCanHandleBreakpoints = {
        width: window.innerWidth,
        handleBreakpoints: (width) => {
          if (width <= 933 && id !== 'menu-button') this.closeMenu();
        },
      };
      browser.handleBreakpoints(browser.width);
    });

    $(document).on('keypress', (e: KeyboardEvent) => {
      if (e.keyCode === 2) {
        this.toggleMenu();
      }
    });
  }

  /**
   * Declare semantic modules.
   *
   * @override
   * @memberof NavigationTopSidebarComponent
   */
  public declareSemanticModules(): void {
    this.declareSidebar();
  }

  /**
   * Declare sidebar module.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private declareSidebar(): void {
    $('.navigation-top-sidebar.component .ui.sidebar')
      .sidebar({ context: '.navigation-top-sidebar.component' });
    this.adjustTopFixedMenu();
    this.makeResponsive();
  }

  /**
   * Listen to resize event and evaluate browser width to make content responsive.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private makeResponsive(): void {
    this.evaluateBrowserWidth();
    window.addEventListener('resize', () =>
      this.evaluateBrowserWidth());
  }

  /**
   * Handle responsiveness of the content by evaluating browser width.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private evaluateBrowserWidth(): void {
    const browser: BrowserCanHandleBreakpoints = {
      width: window.innerWidth,
      handleBreakpoints: (width) => {
        if (width > 933) this.openMenu();
        if (width <= 933) this.closeMenu();
      },
    };

    browser.handleBreakpoints(browser.width);
  }

  /**
   * Simple adjustment for top fixed menu.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private adjustTopFixedMenu(): void {
    setTimeout(() => {
      const sidebarLogoItemHeight = $('.logo.item').css('height');
      $('.ui.top.fixed.menu').css('height', sidebarLogoItemHeight);
      $('.pusher').css('padding-top', sidebarLogoItemHeight);
    }, 10);
  }

  /**
   * Toggle the menu to be opened or closed according to the boolean variable.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private toggleMenu(): void {
    if (this.menuIsOpen) this.closeMenu();
    else this.openMenu();
  }

  /**
   * Close the menu of the side bar.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private closeMenu(): void {
    setTimeout(() => $('.ui.left.sidebar').css('width', 0), 10);
    $('.ui.left.sidebar').children().css('opacity', 0);
    $('.navigation-top-sidebar .top.fixed.menu').css('padding-left', 0);
    $('.pusher').css('padding-left', 0);
    this.menuIsOpen = !this.menuIsOpen;
  }

  /**
   * Open menu of the side bar.
   *
   * @private
   * @memberof NavigationTopSidebarComponent
   */
  private openMenu(): void {
    const browser: BrowserCanHandleBreakpoints = {
      width: window.innerWidth,
      handleBreakpoints: (width) => {
        if (width > 933) {
          $('.ui.left.sidebar').css('width', 260);
          setTimeout(() => $('.ui.left.sidebar').children().css('opacity', 1), 10);
          $('.navigation-top-sidebar .top.fixed.menu').css('padding-left', 260);
          $('.pusher').css('padding-left', 260);
        } else if (width <= 933) {
          $('.ui.left.sidebar').css('width', 260);
          setTimeout(() => {
            $('.ui.left.sidebar').children().css('opacity', 1);
          }, 10);
        }
      },
    };
    browser.handleBreakpoints(browser.width);
    this.menuIsOpen = !this.menuIsOpen;
  }
}
</script>



<template>
  <div class="navigation-top-sidebar component">
    <!-- Top-bar menu -->
    <div class="ui top fixed menu">

      <!-- Toogle sidebar -->
      <div class="item">
        <div v-on:click="toggleMenu()" class="ui big basic icon compact toggle button" data-transition="overlay">
          <i class="sidebar icon"></i>
        </div>
      </div>
      <slot name="top-left-menu-item"></slot>
      <slot name="top-right-menu-item"></slot>
    </div>

    <div class="ui visible left vertical sidebar menu">
      <slot name="app-logo"></slot>
      <slot name="info"></slot>
      <slot name="sidebar-menu"></slot>
    </div>
    <div class="pusher">
      <!-- Site content !-->
      <!-- <div class="ui basic segment"> -->
        <router-view/>
      <!-- </div> -->
    </div>
  </div>
</template>

<style scoped>
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
  background: #f5f6fa;
}

.ui.vertical.sidebar a.logo.item {
  padding: 0.3em;
  background: #2196F3;
}

.ui.vertical.sidebar a.logo.item:hover {
  background: #03A9F4;
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
import { BrowserCanHandleBreakpoints } from '@/iochord/chdsr/common/browser/interfaces/BrowserCanHandleBreakpoints';

// JQuery Symbol Handler
declare const $: any;

@Component
export default class NavigationTopSidebarComponent extends Vue {

  private menuIsOpen: boolean = false;

  private mounted(): void {
    this.$nextTick(() => {
      this.initSidebar();
    });
  }

  private initSidebar(): void {
    $('.navigation-top-sidebar.component .ui.sidebar').sidebar({
      context: '.navigation-top-sidebar.component',
    });
    this.adjustTopFixedMenu();
    this.makeResponsive();
  }

  private makeResponsive(): void {

    this.evaluateBrowserWidth();

    // handle resize
    window.addEventListener('resize', () => {
      this.evaluateBrowserWidth();
    });
  }

  private evaluateBrowserWidth(): void {
    const browser: BrowserCanHandleBreakpoints = {
      width: window.innerWidth,
      handleBreakpoints: (width) => {
        if (width > 933) {
          this.openMenu();
        }
        if (width <= 933) {
          this.closeMenu();
        }
      },
    };

    // Execute custom implementation of handleBreakpoints
    browser.handleBreakpoints(browser.width);
  }

  private adjustTopFixedMenu(): void {
    setTimeout(() => {
      const sidebarLogoItemHeight = $('.ui.vertical.sidebar.menu a.logo.item').css('height');
      $('.ui.top.fixed.menu').css('height', sidebarLogoItemHeight);
      $('.pusher').css('padding-top', sidebarLogoItemHeight);
    }, 10);
  }

  private toggleMenu(): void {
    if (this.menuIsOpen) {
      this.closeMenu();
    } else {
      this.openMenu();
    }
  }

  private closeMenu(): void {

    // Close the menu after 200ms
    setTimeout(() => {
      $('.ui.left.sidebar').css('width', 0);
    }, 200);

    $('.ui.left.sidebar').children().css('opacity', 0);
    $('.navigation-top-sidebar .top.fixed.menu').css('padding-left', 0);
    $('.pusher').css('padding-left', 0);

    // The menu is not opened
    this.menuIsOpen = !this.menuIsOpen;
  }

  private openMenu(): void {

    const browser: BrowserCanHandleBreakpoints = {
      width: window.innerWidth,
      handleBreakpoints: (width) => {
        if (width > 933) {
          $('.ui.left.sidebar').css('width', 260);

          // Display the menu after 200ms
          setTimeout(() => {
            $('.ui.left.sidebar').children().css('opacity', 1);
          }, 200);

          $('.navigation-top-sidebar .top.fixed.menu').css('padding-left', 260);
          $('.pusher').css('padding-left', 260);
        } else if (width <= 933) {
          $('.ui.left.sidebar').css('width', 260);

          // Display the menu after 200ms
          setTimeout(() => {
            $('.ui.left.sidebar').children().css('opacity', 1);
          }, 200);

        }
      },
    };

    browser.handleBreakpoints(browser.width);

    // The menu is not closed
    this.menuIsOpen = !this.menuIsOpen;
  }

}
</script>



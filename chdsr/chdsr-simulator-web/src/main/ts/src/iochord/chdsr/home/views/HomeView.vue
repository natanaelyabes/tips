<template>
  <div class="home view">
    <ApplicationWrapper >
      <template slot="application-header-breadcrumb">
        <div class="active section">{{title}}</div>
      </template>
      <template slot="application-content">
        <div class="ui basic segment">
          <div class="ui stackable three cards">
            <div class="ui card">
              <div class="content">
                <h2 class="left floated">Getting Started</h2>
                <div class="right floated">
                  <!-- <div class="ui basic compact icon button"><i class="plus icon"></i></div> -->
                </div>
                <div class="description">
                  <p><a href="#" style="text-decoration: underline">Connect to a data source</a></p>
                  <p><a href="#" style="text-decoration: underline">Create a simulation model</a></p>
                  <p><a href="#" style="text-decoration: underline">Discover simulation model</a></p>
                  <p><a href="#" style="text-decoration: underline">Create a hybrid simulation model</a></p>
                </div>
              </div>
              <div class="extra content">
                <div class="left floated">
                  <span><router-link to="/bsclab/synchronous/wiki/getting-started"><i class="info icon"></i>Help</router-link></span>
                </div>
              </div>
            </div>
            <div class="ui card">
              <div class="content">
                <h2 class="left floated">Recent Simulations</h2>
                <div class="right floated">
                  <div class="ui basic compact icon button"><i class="plus icon"></i></div>
                </div>
                <div class="description">
                  <div class="ui middle aligned divided list">
                    <!-- router links -->
                    <p>No recent simulations available.</p>
                  </div>
                </div>
              </div>
              <div class="extra content">
                <div class="left floated">
                  <span><router-link to="/bsclab/synchronous/workflow/models" tag="a"><i class="ellipsis horizontal icon"></i>More</router-link></span> &nbsp;
                  <span><a href="#"><i class="filter icon"></i> Filter</a></span>
                </div>
              </div>
            </div>
            <div class="ui card">
              <div class="content">
                <h2 class="left floated">Recent Files</h2>
                <div class="right floated">
                  <div class="ui basic compact icon button"><i class="plus icon"></i></div>
                </div>
                <div class="description">
                  <div class="ui middle aligned divided list">
                    <!-- router links -->
                    <p>No recent files available.</p>
                  </div>
                </div>
              </div>
              <div class="extra content">
                <div class="left floated">
                  <span><router-link to="/bsclab/synchronous/workflow/instances" tag="a"><i class="ellipsis horizontal icon"></i>More</router-link></span> &nbsp;
                  <span><a href="#"><i class="filter icon"></i> Filter</a></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </template>
    </ApplicationWrapper>
  </div>
</template>

<style>
.home.view {
  background: gainsboro;
  background-size: cover;
  height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Enums
import { ApplicationEnum, BaseUrlEnum } from '@/iochord/chdsr/common/enums/index';

// Interfaces
import { ApplicationHasWrapper } from '../../common/ui/application/interfaces/ApplicationHasWrapper';
import { Breadcrumb } from '../../common/ui/semantic/breadcrumbs/interfaces/Breadcrumb';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';

// Components
import ApplicationWrapper from '@/iochord/chdsr/common/ui/application/components/ApplicationWrapperComponent.vue';
import { setTimeout } from 'timers';

@Component({
  components: {
    ApplicationWrapper,
  },
})
export default class HomeView extends Vue
implements ApplicationHasWrapper, SemanticModulesIsUsed, BrowserHasProperties {
  public title: string = '';
  public breadcrumbs!: Breadcrumb[];
  public titleMenuBarItems: any;
  public leftMenuBarItems: any;
  public rightMenuBarItems: any;
  public ribbonMenuItems: any;
  public content: any;

  public mounted(): void {
    this.overrideProperties();
    this.setApplicationWrapperProperties();

    // Un-implemented
    this.declareSemanticModules();
  }

  public overrideProperties(): void {
    document.title = `${BaseUrlEnum.IOCHORD}/${ApplicationEnum.NAME.toUpperCase()} · Home`;
  }

  public declareSemanticModules(): void {
    // throw new Error('Method not implemented.');
  }

  public setTitle(): void {
    this.title = 'Home';
  }

  public setBreadcrumb(): void {
    // throw new Error('Method not implemented.');
  }

  public setTitleMenubar(): void {
    // throw new Error('Method not implemented.');
  }

  public setLeftMenuSidebar(): void {
    // throw new Error('Method not implemented.');
  }

  public setRightMenuSidebar(): void {
    // throw new Error('Method not implemented.');
  }

  public setRibbonMenuItem(): void {
    // throw new Error('Method not implemented.');
  }

  public setContent(): void {
    // throw new Error('Method not implemented.');
  }

  public setApplicationWrapperProperties(): void {
    this.setTitle();
    this.setBreadcrumb();
    this.setTitleMenubar();
    this.setLeftMenuSidebar();
    this.setRightMenuSidebar();
    this.setRibbonMenuItem();
    this.setContent();
  }

}
</script>

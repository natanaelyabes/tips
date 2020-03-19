<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="home view">
    <WrapperComponent>
      <template slot="header-breadcrumb">
        <div class="active section">{{title}}</div>
      </template>
      <template slot="content">
        <div class="ui basic segment">
          <div class="ui stackable three cards">
            <div class="ui card">
              <div class="content">
                <h2 class="left floated">Getting Started</h2>
                <div class="description">
                  <p><router-link tag="a" style="text-decoration: underline" class="item" :to="'/iochord/ips/data/connection'">Connect to a data source</router-link></p>
                  <p><router-link tag="a" style="text-decoration: underline" class="item" :to="'/iochord/ips/simulation/editor'">Create a simulation model</router-link></p>
                  <p><a href="#" style="text-decoration: underline">Discover simulation model</a></p>
                  <p><a href="#" style="text-decoration: underline">Create a hybrid simulation model</a></p>
                  <p><a href="#" style="text-decoration: underline">Analyze data</a></p>
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
    </WrapperComponent>
  </div>
</template>

<style>
/**
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
.home.view {
  background: gainsboro;
  background-size: cover;
  height: 100%;
}

@media only screen and (max-width: 767.99px) {
  .ui.stackable.cards .card:first-child {
    margin-top: 0!important;
    
  }  
}

</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Classes
import AppLayoutView from '@/iochord/ips/common/ui/layout/class/AppLayoutView';

// Interfaces
import { AppLayout } from '@/iochord/ips/common/ui/layout/interfaces/AppLayout';

// Components
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';

@Component({
  components: {
    WrapperComponent,
  },
})
/**
 * The home view.
 *
 * @export
 * @class HomeView
 * @extends {AppLayoutView}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class HomeView extends AppLayoutView {

  /**
   * Override the browser properties.
   *
   * @memberof HomeView
   */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Home');
  }

  /**
   * Assigns title to the home view page.
   *
   * @memberof HomeView
   */
  public setTitle(): void {
    this.title = 'Home';
  }
}
</script>

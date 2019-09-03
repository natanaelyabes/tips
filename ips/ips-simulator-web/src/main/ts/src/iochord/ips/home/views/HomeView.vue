<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
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
    </WrapperComponent>
  </div>
</template>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.home.view {
  background: gainsboro;
  background-size: cover;
  height: 100%;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Classes
import Layout01 from '@/iochord/ips/common/ui/layout/class/Layout01';

// Interfaces
import { Layout01HasWrapper } from '../../common/ui/layout/interfaces/Layout01HasWrapper';

// Components
import WrapperComponent from '@/iochord/ips/common/ui/layout/components/WrapperComponent.vue';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component({
  components: {
    WrapperComponent,
  },
})
export default class HomeView extends Layout01 {
  /** @Override */
  public overrideBrowserProperties(): void {
    this.setDocumentTitle('Home');
  }

  /** @Override */
  public setTitle(): void {
    this.title = 'Home';
  }
}
</script>

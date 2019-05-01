<template>
  <div class="home view">
    <ApplicationWrapper >
      <template slot="application-header-breadcrumb">
        <div class="active section">Home</div>
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
  background: url('https://images.pond5.com/blue-white-gradient-polygonal-surface-footage-073466103_prevstill.jpeg') fixed center center;
  background-size: cover;
  height: 100%;
  filter: hue-rotate(20deg);
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Enums
import { ApplicationEnum } from '@/iochord/chdsr/common/enums/index';

// Interfaces
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';
import { BrowserHasProperties } from '@/iochord/chdsr/common/browser/interfaces/BrowserHasProperties';

// Components
import ApplicationWrapper from '@/iochord/chdsr/common/ui/application/components/ApplicationWrapperComponent.vue';

@Component({
  components: {
    ApplicationWrapper,
  },
})
export default class Home extends Vue implements VueHasLifeCycle, SemanticModulesIsUsed, BrowserHasProperties {
  public beforeCreate(): void {
    // throw new Error("Method not implemented.");
  }

  public created(): void {
    // throw new Error("Method not implemented.");
  }

  public beforeMount(): void {
    // throw new Error("Method not implemented.");
  }

  public mounted(): void {
    this.fn_browser_override_properties();
    this.fn_semantic_declare_modules();
  }

  public beforeUpdate(): void {
    // throw new Error("Method not implemented.");
  }

  public updated(): void {
    // throw new Error("Method not implemented.");
  }

  public beforeDestroy(): void {
    // throw new Error("Method not implemented.");
  }

  public destroyed(): void {
    // throw new Error("Method not implemented.");
  }

  public fn_browser_override_properties(): void {
    document.title = `${ApplicationEnum.NAME} | Home`;
  }

  public fn_semantic_declare_modules(): void {
    // throw new Error("Method not implemented.");
  }

}
</script>

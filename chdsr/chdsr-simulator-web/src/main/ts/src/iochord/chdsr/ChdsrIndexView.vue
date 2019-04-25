<template>
  <div class="index view">
    <NavigationTopSidebarComponent>
      <template slot="top-right-menu-item">
        <!-- Right menu item -->
        <div class="right menu">
          <router-link tag="a" to="/iochord" class="item icon" data-inverted="" data-content="Data Management" data-position="bottom right"><i class="save icon"></i></router-link>
          <router-link tag="a" to="/iochord" class="item icon" data-inverted="" data-content="Data Filtering" data-position="bottom right"><i class="filter icon"></i></router-link>
          <div class="ui dropdown item">
            <img class="ui avatar image" src="@/assets/images/avatars/steve.jpg">
              Administrator
            <i class="dropdown icon"></i>
            <div class="menu">
              <!-- <a class="item"><i class="user icon"></i> Profile</a> -->
              <!-- <a class="item"><i class="cogs icon"></i> Settings</a> -->
              <a class="item" v-on:click="logout()"><i class="power icon"></i> Logout</a>
            </div>
          </div>
        </div>
      </template>
      <div slot="app-logo">
        <router-link to="/iochord" tag="a" class="logo item">
          <img id="chdsr-logo" class="ui centered small image" src="@/assets/images/logos/CHDSR-white-on-transparent.png" alt="CHDSR main logo">
        </router-link>
      </div>
      <!--  -->
      <div slot="sidebar-menu">
        <router-link tag="a" class="item" to="/iochord/chdsr">Home <i class="home icon"></i></router-link>
        <div class="item">
          <div class="header" style="text-decoration:underline">Data Management</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Upload / Open <i class="save icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Connection <i class="bolt icon"></i></router-link>
            <router-link tag="a" class="item" to="#">History / Browser <i class="list icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Filter <i class="filter icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Mapping <i class="map signs icon"></i></router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Analysis / Algorithm</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Process Model <i class="project diagram icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Branches Mining <i class="code branch icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Resource Mining <i class="users icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Distribution Fitting <i class="chart area icon"></i></router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Simulation Generation</div>
          <div class="menu">
            <router-link tag="a" class="item" to="#">Simulation Discovery <i class="globe icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Hybrid Simulation <i class="sitemap icon"></i></router-link>
          </div>
        </div>
        <div class="item">
          <div class="header" style="text-decoration:underline">Simulation Editor</div>
          <div class="menu">
            <router-link tag="a" class="item" to="/iochord/chdsr/simulation-editor">Editor <i class="pencil icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Report <i class="file outline alternate icon"></i></router-link>
            <router-link tag="a" class="item" to="#">Export <i class="file export icon"></i></router-link>
          </div>
        </div>
      </div>
    </NavigationTopSidebarComponent>
  </div>
</template>

<style>
.index.view {
  height: 100%;
}

.index.view img#chdsr-logo {
  padding: 1em 0;
}
</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

// Interfaces
import { VueHasLifeCycle } from '@/iochord/chdsr/common/lib/vue/interfaces/VueHasLifeCycle';
import { SemanticModulesIsUsed } from '@/iochord/chdsr/common/ui/semantic/SemanticModulesIsUsed';

// Components
import NavigationTopSidebarComponent from '@/iochord/chdsr/common/ui/semantic/navigations/components/NavigationTopSidebarComponent.vue';

// JQuery Symbol Handler
declare const $: any;

@Component({
  components: {
    NavigationTopSidebarComponent,
  },
})
export default class Index extends Vue implements VueHasLifeCycle, SemanticModulesIsUsed {
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
    this.$nextTick(() => {
      this.fn_semantic_declare_modules();
    });
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

  public fn_semantic_declare_modules(): void {
    this.fn_semantic_declare_dropdown();
  }

  private fn_semantic_declare_dropdown(): void {
    $('.ui.dropdown').dropdown();
  }

  private logout(): void {
    this.$router.push({name: 'iochord-chdsr-login'});
  }
}
</script>

<template>
  <div class="depth two component">
    <!-- Depth One Menu Item -->
    <SidebarComponent>
      <template slot="sidebar-menu-item">
        <slot name="depth-one-menu-item"></slot>
      </template>
    </SidebarComponent>

    <!-- Depth Two Menu Item -->
    <SidebarComponent>
      <template slot="sidebar-menu-item">
        <slot name="depth-two-menu-item"></slot>
      </template>
    </SidebarComponent>


    <div class="ui basic segment">
      <slot name="content"></slot>
    </div>




    <!-- <template name="depth-two-menu">
      <slot name="depth-two-menu">dsdksdjk</slot>
    </template> -->
  </div>


</template>

<style scoped>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.depth.two.component {
  height: 100%;
  overflow: hidden;
  max-height: 100%;
  position: relative;
  display: flex;
}
</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '../../../common/ui/layout/classes/BaseComponent';
import SidebarComponent from '../../../common/ui/layout/components/SidebarComponent.vue';

@Component({
  components: {
      SidebarComponent
  },
})
export default class DepthTwoLeftWorkspaceComponent extends BaseComponent {

}
</script>

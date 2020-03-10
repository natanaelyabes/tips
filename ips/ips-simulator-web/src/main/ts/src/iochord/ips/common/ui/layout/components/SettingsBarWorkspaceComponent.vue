<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="settings bar wrapper component">

    <!-- Ribbon Menu Item -->
    <RibbonComponent id="ribbon-bar">
      <template slot="ribbon-menu-item">
        <slot name="ribbon-bar-menu-item"></slot>
      </template>
    </RibbonComponent>

    <!-- Content -->
    <div class="content">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<style>
.ribbon.bar {
  height: 30%;
  width: 100%;
  max-height: 100%;
  display: flex;
}

.content {
  height: 100%;
}

.ui.basic.segment {
  overflow-y: scroll;
  width: 100%;
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import RibbonComponent from '@/iochord/ips/common/ui/layout/components/RibbonComponent.vue';

@Component({
  components: {
    RibbonComponent,
  },
})

/**
 * Settings bar workspace component.
 *
 * @export
 * @class SettingsBarWorkspaceComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class SettingsBarWorkspaceComponent extends BaseComponent {

}

</script>

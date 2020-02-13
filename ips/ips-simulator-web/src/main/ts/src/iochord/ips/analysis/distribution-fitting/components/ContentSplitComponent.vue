<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="content split component">
    <SplitContentWrapperComponent>
      <template slot="content-left">
        <div class="ui basic segment">
          <h4 class="ui dividing header">Process model</h4>
          process model canvas
        </div>
      </template>

      <template slot="content-right">
        <div class="ui basic segment">
        <h4 class="ui dividing header">Fitted</h4>
          parameters
        </div>
      </template>
    </SplitContentWrapperComponent>
  </div>
</template>

<style scoped>

.ui.basic.segment {
  overflow-y: scroll;
  display: inline-block;
  height: 100%;
  width: 50%;
}
</style>

<style>
.content.split.component {
  height: 100%;
}
</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SplitContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/SplitContentWrapperComponent.vue';

@Component({
  components: {
    SplitContentWrapperComponent,
  },
})

/**
 * Split component to provide a side by side
 * view between process model and its fitted distribution.
 *
 * @extends BaseComponent
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class ContentSplitComponent extends BaseComponent {

}
</script>

<template>
  <div class="wrapper component">
    <SplitContentComponent>
      <template slot="content-left">
        <slot name="content-left"></slot>
        <!-- <div class="ui basic segment">
          left
        </div> -->
      </template>

      <template slot="content-right">
        <slot name="content-right"></slot>
        <!-- <div class="ui basic segment">
          right
        </div> -->
      </template>
    </SplitContentComponent>
    <!-- <div class="ui basic segment">
      left
    </div>

    <div class="ui basic segment">
      left
    </div> -->
  </div>
</template>

<style scoped>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.wrapper.component {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import SplitContentComponent from './SplitContentComponent.vue';

@Component({
  components: {
    SplitContentComponent,
  },
})
export default class SplitContentWrapperComponent extends BaseComponent {

}
</script>

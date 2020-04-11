<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="content split component">
    <LeftBarContentWrapperComponent>
      <template slot="left-bar-menu-item">
        <ItemBranchList />
      </template>

      <template slot="content">
        <ItemProcessModel />
      </template>
    </LeftBarContentWrapperComponent>
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
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import LeftBarContentWrapperComponent from '@/iochord/ips/common/ui/layout/components/LeftBarContentWrapperComponent.vue';
import ItemProcessModel from './ItemProcessModel.vue';
import ItemBranchList from './ItemBranchList.vue';

@Component({
  components: {
    LeftBarContentWrapperComponent,
    ItemProcessModel,
    ItemBranchList,
  },
})

/**
 * Split component to provide a side by side view
 * between process model and its branching rule.
 *
 * @export
 * @class ContentSplitComponent
 * @extends SemanticComponent
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 *
 */
export default class ContentSplitComponent extends SemanticComponent {
  //
}
</script>

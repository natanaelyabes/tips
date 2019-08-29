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
    <!-- <div class="ui basic segment">
      left
    </div>

    <div class="ui basic segment">
      left
    </div> -->
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
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';
import SplitContentWrapperComponent from '../../SandboxAnalysisBranch/components/SplitContentWrapperComponent.vue';

@Component({
  components: {
    SplitContentWrapperComponent,
  },
})
export default class ContentSplitComponent extends BaseComponent {

}
</script>

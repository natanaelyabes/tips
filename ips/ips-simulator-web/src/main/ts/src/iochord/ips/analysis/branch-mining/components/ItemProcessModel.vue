<template>
  <div class="item process model component">
    <ModelViewer ref="viewer"></ModelViewer>
  </div>
</template>

<style scoped>

</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import ModelViewer from '@/iochord/ips/simulation/editor/components/ModelViewer.vue';
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import { getModule } from 'vuex-module-decorators';

// Vuex module
const graphModule = getModule(GraphModule);

@Component({
  components: {
    ModelViewer,
  },
})
export default class ItemProcessModel extends SemanticComponent {
  //
}
</script>
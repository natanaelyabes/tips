<template>
  <div class="banner component">
    <slot name="content"></slot>
    <!-- <img class="ui large image" src="@/assets/images/logos/bsclab_logo_transparent_color.png" alt="BSCLab Logo"> -->
  </div>
</template>

<style scoped>

</style>

<script lang="ts">
// Vue & Libraries
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class BannerComponent extends Vue {

}
</script>

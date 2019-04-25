<template>
  <div class="navigation-bar component">
    <div v-bind:class="[type, color, size]" class="ui menu">
      <slot name="menu-item"></slot>
    </div>
  </div>
</template>

<style scoped>
.ui.menu {
  width: 100%;
}
</style>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class NavigationBarComponent extends Vue {
  @Prop() private type!: string;
  @Prop() private color!: string;
  @Prop() private size!: string;
}
</script>


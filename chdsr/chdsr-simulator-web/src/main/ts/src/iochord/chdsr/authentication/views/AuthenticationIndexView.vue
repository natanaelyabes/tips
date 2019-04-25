<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<style>
.index.view {
  height: 100%;
}
</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class AuthenticationIndexView extends Vue {

}
</script>

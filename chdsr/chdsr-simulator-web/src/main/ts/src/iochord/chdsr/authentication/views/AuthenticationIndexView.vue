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
import { Component } from 'vue-property-decorator';
import IndexView from '@/iochord/chdsr/common/lib/vue/classes/IndexView';

@Component
export default class AuthenticationIndexView extends IndexView {
  //
}
</script>

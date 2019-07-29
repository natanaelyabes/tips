<template>
  <div class="index view">
    <router-view></router-view>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import IndexLayout from '@/iochord/chdsr/common/ui/layout/classes/IndexLayout';

@Component
export default class SandboxListVueRouterIndex extends IndexLayout {

}
</script>

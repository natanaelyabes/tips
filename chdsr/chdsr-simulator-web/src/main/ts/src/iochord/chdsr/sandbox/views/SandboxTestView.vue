<template>
  <div class="test view">
    <div class="ui basic segment">
      <h1>Sandbox Test Works! {{ test }}</h1>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseView from '@/iochord/chdsr/common/lib/vue/classes/BaseView';

import SocketIO from 'socket.io-client';

@Component
export default class SandboxTestView extends BaseView {
  public test: any = 'test';

  /** @Override */
  public mounted(): void {
    const socket = SocketIO('http://164.125.62.134:3002');
    socket.emit('sandbox test', { test: 'test payload' });

    socket.on('sandbox test message', (data: any) => {
      this.test = data;
    });
  }
}
</script>

<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <div class="test view">
    <div class="ui basic segment">
      <h1>Sandbox Test Works! <span data-tooltip="From socket.io" data-position="bottom center" data-inverted class="socket-io">{{ test }}</span></h1>
    </div>
  </div>
</template>

<style>
/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
.socket-io {
  color: green;
  cursor: pointer;
}
</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseLayout from '@/iochord/ips/common/ui/layout/class/BaseLayout';

// import SocketIO from 'socket.io-client';


/**
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component

/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class SandboxTestView extends BaseLayout {
  public test: any = 'test';

  /** @Override */
  public mounted(): void {
    // const socket = SocketIO('http://localhost:3001');

    // socket.emit('sandbox test', { test: 'test payload' });

    // socket.on('sandbox test message', (data: any) => {
    //   this.test = data;
    // });
  }
}
</script>

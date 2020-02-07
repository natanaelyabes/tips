<!--
  @package ips
  @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
  @since 2019
-->
<template>
  <router-view></router-view>
</template>

<script lang="ts">
import { Component, Vue  } from 'vue-property-decorator';

@Component

/**
 * Base layout for IPS application.
 *
 * @export
 * @class BaseLayout
 * @extends {Vue}
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 */
export default class BaseLayout extends Vue {
  //
}
</script>

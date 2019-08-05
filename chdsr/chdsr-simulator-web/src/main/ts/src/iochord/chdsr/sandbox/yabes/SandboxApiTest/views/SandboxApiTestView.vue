<template>
  <div class="sandbox api test view">
    <h1>{{ responseStatus }}</h1>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Axios, { AxiosResponse } from 'axios';


@Component
export default class SandboxApiTestView extends Vue {

  private response?: AxiosResponse<any>;

  public async mounted(): Promise<void> {
    this.response = await Axios.get('http://dummy.restapiexample.com/api/v1/employees');
    console.log(this.response);
  }

  public get responseStatus(): number {
    return (this.response as AxiosResponse).status;
  }
}
</script>

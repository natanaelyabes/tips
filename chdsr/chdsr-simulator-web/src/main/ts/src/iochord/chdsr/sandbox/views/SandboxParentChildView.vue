<template>
  <div class="sandbox parent child view">
    <div class="ui basic segment">
      <h1>Parent-Child Data Binding</h1>
      <FormComponent
        :firstName.sync="firstName"
        :lastName.sync="lastName"
        @changeFirstName="firstNameFromChild($event)"
        @changeLastName="lastNameFromChild($event)"
      />
    </div>
  </div>
</template>

<style>

</style>


<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseComponent from '@/iochord/chdsr/common/lib/vue/classes/BaseComponent';
import FormComponent from '@/iochord/chdsr/sandbox/components/FormComponent.vue';

@Component({
  components: {
    FormComponent,
  },
})
export default class SandboxParentChildView extends BaseComponent {
  private firstName: string = '';
  private lastName: string = '';

  public mounted(): void {
    this.firstName = 'Patrick';
    this.lastName = 'Star';
  }

  public firstNameFromChild(e: any) {
    console.log(e);
    this.firstName = e;
  }

  public lastNameFromChild(e: any) {
    console.log(e);
    this.lastName = e;
  }
}
</script>

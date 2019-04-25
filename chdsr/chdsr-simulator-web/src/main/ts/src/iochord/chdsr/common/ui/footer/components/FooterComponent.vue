<template>
  <div class="footer component">
    <footer>
      <p>Copyright &copy; {{currentYear}} Business Service Computing Laboratory. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
.footer.component{
  position: static;
  bottom: 1em;
  margin: 2em 0em 0em;
}
</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class FooterComponent extends Vue {
  private currentDate: Date = new Date();

  private get currentYear(): number {
    return this.currentDate.getFullYear();
  }
}
</script>

<template>
  <div class="content data upload component">
    <form ref="frmUpload" class="ui form">
      <h4 class="ui dividing header">Connection Name</h4>
        <div class="fields">
          <div class="eight wide field">
            <input type="text" name="connect[name]" placeholder="Upload connection name">
          </div>
        </div>
        <div class="fields">
          <div class="eight wide field">
            <label>Filename</label>
            <input ref="file" :disabled="isUploading" type="file" accept=".csv" name="connect[name]" placeholder="filename">
          </div>
        </div>
        <div class="inline fields">
          <div class="eight wide field">
            <button type="button" class="ui disabled button">
              Use file
            </button>
            <button type="button" :disabled="isUploading" class="ui primary button" @click="doImport()">
              Import {{uploadStatus}}
            </button>
            <button type="button" class="ui button">
              Cancel
            </button>
          </div>
          <div class="eight wide field">

          </div>
        </div>
    </form>

    <h4 class="ui dividing header">Data Preview</h4>
      <div style="overflow-x: hidden; overflow-y: scroll; border: 1px solid rgba(34,36,38,.15); height: 500px; width: 100%">
        <table class="ui celled striped table">
          <thead>
            <tr>
              <th>Column 1</th>
              <th>Column 2</th>
              <th>Column 3</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td class="collapsing">
                node_modules
              </td>
              <td>Initial commit</td>
              <td class="right aligned collapsing">10 hours ago</td>
            </tr>
          </tbody>
        </table>
      </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import DataConnectionService from '@/iochord/ips/common/service/data/DataConnectionService';

@Component
export default class ContentDataUploadComponent extends BaseComponent {

  public isUploading: boolean = false;
  public uploadStatus: string = '';

  public doImport() {
    const self = this;
    const frmUpload = self.$refs['frmUpload'] as HTMLFormElement;
    const ele = this.$refs['file'] as HTMLInputElement;
    const files = ele.files;
    if (files != null && files.length > 0) {
      const data = new FormData();
      data.append('file', files[0]);
      self.isUploading = true;
      DataConnectionService.getInstance().importCsv(data, (res: any) => {
        self.isUploading = false;
        self.uploadStatus = '';
        frmUpload.reset();
      }, (tick: any) => {
        self.uploadStatus = tick.body;
      });
    }
  }

  /** @Override */
  public async mounted(): Promise<void> {
    //
  }
}

</script>


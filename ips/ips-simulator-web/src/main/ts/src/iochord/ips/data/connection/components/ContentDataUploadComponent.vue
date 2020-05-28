<!--
  @package ts
  @author R. A. Sutrisnowati <riska@iochord.com>
  @since 2019
-->
<template>
  <div class="content data upload component">
    <form ref="frmUpload" class="ui form">
      <h4 class="ui dividing header">Import CSV</h4>
        <div class="fields">
          <div class="eight wide field">
            <label>Dataset Name </label>
            <input type="text" v-model="config.name" placeholder="Dataset name">
          </div>
        </div>
        <div class="fields">
          <div class="eight wide field">
            <label>Cell Delimeter</label>
            <input type="text" v-model="config.delimeter" placeholder="">
          </div>
        </div>
        <div class="fields">
          <div class="eight wide field">
            <label>Header Row Index (First Row = 0, -1 = No Header Row)</label>
            <input type="number" min="-1" tick="1" v-model="config.headerRowIdx" placeholder="">
          </div>
        </div>
        <div class="fields">
          <div class="eight wide field">
            <label>File</label>
            <input ref="file" :disabled="isUploading" type="file" accept=".csv" name="connect[name]" placeholder="filename">
          </div>
        </div>
        <br />
        <div class="inline fields">
          <div class="eight wide field">
            <button type="button" :disabled="isUploading" class="ui primary button" @click="doImport()">
              Import {{uploadStatus}}
            </button>
            <button type="button" class="ui button">
              Cancel
            </button>
          </div>
        </div>
    </form>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import DataConnectionService, { ImportCsvConfiguration } from '@/iochord/ips/data/connection/services/DataConnectionService';

@Component

/**
 * Content data upload component.
 *
 * @export
 * @class ContentDataUploadComponent
 * @extends {BaseComponent}
 *
 * @package ts
 * @author R. A. Sutrisnowati <riska@iochord.com>
 * @since 2019
 */
export default class ContentDataUploadComponent extends BaseComponent {

  /**
   * Status to indicate whether current component is in the uploading state. False otherwise.
   *
   * @type {boolean}
   * @memberof ContentDataUploadComponent
   */
  public isUploading: boolean = false;

  /**
   * Status to indicate the upload state.
   *
   * @type {string}
   * @memberof ContentDataUploadComponent
   */
  public uploadStatus: string = '';

  /**
   * CSV import configuration.
   *
   * @type {ImportCsvConfiguration}
   * @memberof ContentDataUploadComponent
   */
  public config: ImportCsvConfiguration = new ImportCsvConfiguration();

  /**
   * Import CSV from local files.
   *
   * @memberof ContentDataUploadComponent
   */
  public doImport() {
    const self = this;
    const frmUpload = self.$refs['frmUpload'] as HTMLFormElement;
    const ele = this.$refs['file'] as HTMLInputElement;
    const files = ele.files;
    if (files != null && files.length > 0) {
      self.isUploading = true;
      DataConnectionService.getInstance().importCsv(this.config, files[0],
      (res: any) => {
        self.isUploading = false;
        self.uploadStatus = '';
        // frmUpload.reset();
        self.$router.push('/iochord/ips/data/connection');
      }, (tick: any) => {
        self.uploadStatus = tick.progress;
      });
    }
  }
}
</script>

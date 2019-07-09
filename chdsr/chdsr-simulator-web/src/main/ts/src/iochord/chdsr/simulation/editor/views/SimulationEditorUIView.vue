<!--
  @package chdsr
  @author Taufik Nur Adi <taufik.nur.adi@gmail.com>
  @since 2019
-->
<template>
  <div class="simulation editor test view">
    <div class="ui basic segment">
      <h1>Simulation Editor Test</h1>
      <div id="mypaper"></div>
    </div>

    <!-- Change for Recommit -->
    <!-- Modal for start node -->
    <StartNodeModal/>

    <!-- Modal for stop node -->
    <StopNodeModal/>

    <!-- Modal for activity -->
    <ActivityNodeModal />

    <!-- Object Type -->
    <ObjectTypeDataModal/>

    <!-- Generator -->
    <GeneratorDataModal/>

    <!-- Function -->
    <FunctionDataModal/>

    <!-- Queue -->
    <QueueDataModal/>

    <!-- Resource -->
    <ResourceDataModal/>

    <!-- Branches -->
    <BranchNodeModal />

    <!-- Simulation Configuration -->
    <ConfigurationModal />

    <!-- Simulation Control -->
    <ControlModal />
  </div>
</template>

<style>
/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

//  Components
import StartNodeModal from '../../../common/kpi/components/modals/StartNodeModal.vue';

declare const $: any;


/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
@Component
export default class SimulationEditorUIView extends Vue {
  private selectedGate = '';
  private selectedSJ = '';
  private selectedRule = '';
  private selectedActivityType = '';

  private updated(): void {
    /*
      Branches

      if Gate ===XOR and Type = split
      Then show specific form at the bottom part
    */
    if (this.selectedGate === 'xor' && this.selectedSJ === 'split') {
      $('#row_branches_rule').attr('style', 'visibility:visible');
      $('#row_branches_if').attr('style', 'visibility:visible');
      $('#row_branches_tbl').attr('style', 'visibility:visible');
    } else {
      $('#row_branches_rule').attr('style', 'visibility:hidden');
      $('#row_branches_if').attr('style', 'visibility:hidden');
      $('#row_branches_tbl').attr('style', 'visibility:hidden');
    }

    /*
      Activity

      if Activity Type ===standard Then
      if Activity Type ===concurrent Then
      else (Activity Type ===split module)
    */
    if (this.selectedActivityType === 'standard') {
      $('#basic-standard-sm-1').attr('style', 'visibility:visible');
      $('#basic-cbp-sm-1').attr('style', 'visibility:hidden');
      $('#basic-cbp-sm-2').attr('style', 'visibility:hidden');
      $('#basic-cbp-sm-3').attr('style', 'visibility:hidden');

      $('#basic-split-sm-1').attr('style', 'visibility:hidden');
      $('#basic-split-sm-2').attr('style', 'visibility:hidden');
      $('#basic-split-sm-3').attr('style', 'visibility:hidden');
      $('#basic-split-sm-3').attr('style', 'visibility:hidden');
    } else if (this.selectedActivityType === 'concurrent') {
      $('#basic-standard-sm-1').attr('style', 'visibility:hidden');

      $('#basic-cbp-sm-1').attr('style', 'visibility:visible');
      $('#basic-cbp-sm-2').attr('style', 'visibility:visible');
      $('#basic-cbp-sm-3').attr('style', 'visibility:visible');

      $('#basic-split-sm-1').attr('style', 'visibility:hidden');
      $('#basic-split-sm-2').attr('style', 'visibility:hidden');
      $('#basic-split-sm-3').attr('style', 'visibility:hidden');
      $('#basic-split-sm-3').attr('style', 'visibility:hidden');
    } else {
      $('#basic-standard-sm-1').attr('style', 'visibility:hidden');

      $('#basic-cbp-sm-1').attr('style', 'visibility:hidden');
      $('#basic-cbp-sm-2').attr('style', 'visibility:hidden');
      $('#basic-cbp-sm-3').attr('style', 'visibility:hidden');

      $('#basic-split-sm-1').attr('style', 'visibility:visible');
      $('#basic-split-sm-2').attr('style', 'visibility:visible');
      $('#basic-split-sm-3').attr('style', 'visibility:visible');
      $('#basic-split-sm-4').attr('style', 'visibility:visible');
    }
  }
  private mounted(): void {
    $('.ui.dropdown').dropdown();
    $('.tabular.menu .item').tab();

    /* JQUery Event Handler */
    /* Add Rule for Branches Form */
    $('#btn_add_rule').click(() => {
      let new_row = '<tr>';
      new_row += '<td>Case.loan > 5000 and case.bank = "A"</td>';
      new_row += '<td>';
      new_row += '<button id="btn_del_rule" class="ui negative basic button">';
      new_row += '<i class="close icon"></i>';
      new_row += '</button>';
      new_row += '</td>';
      new_row += '</tr>';
      $('#tb_add_row').append(new_row);
    });

    $('#btn_del_rule').click(() => {
      // $('#btn_del_rule').closest('tr').remove();
    });

    /*
      Branches
      - Delete Button
    */
    $('#row_branches_tbl').on('click', '#btn_del_rule', () => {
      $('#btn_del_rule').closest('tr').remove();
      // alert("tes");
    });

    /* Simulation Configuration */
    $('#sim_config_generate').click(() => {
      const rsc_val = $('#rsc_txt').val();
      let gen_tbl = '';

      $('#sim_config_tbl').html(gen_tbl);

      let head_cols = '';
      let body_cols = '';

      const cols = rsc_val;
      for (let i = 0; i <= cols; i++) {
        head_cols += '<th>Config ' + (i + 1) + '</th>';
        body_cols += '<td><button class="ui button">...</button></td>';
      }

      gen_tbl += '<table class="ui celled compact table">';
      gen_tbl += '<thead>';
      gen_tbl += '<tr>';
      gen_tbl += '<th>Module</th>';
      gen_tbl += head_cols;
      gen_tbl += '</tr>';
      gen_tbl += '</thead>';
      gen_tbl += '<tbody>';
      gen_tbl += '<tr>';
      gen_tbl += '<td>Start</td>';
      gen_tbl += body_cols;
      gen_tbl += '</tr>';
      gen_tbl += '<tr>';
      gen_tbl += '<td>End</td>';
      gen_tbl += body_cols;
      gen_tbl += '</tr>';
      gen_tbl += '</tbody>';
      gen_tbl += '</table>';

      $('#sim_config_tbl').html(gen_tbl);
    });

    const graph = new joint.dia.Graph();

    const paper = new joint.dia.Paper({
      el: document.getElementById('mypaper'),
      model: graph,
      width: window.innerWidth,
      height: window.innerHeight,
      gridSize: 1,
    });

    /* Graph's Elements */
    const standar = joint.shapes.standard;

    const c_start = new standar.Circle();
    c_start.resize(20, 20);
    c_start.position(100, 50);
    c_start.attr('root/title', 'start');
    c_start.attr('body/fill', 'white');
    c_start.addTo(graph);

    const r_br1 = new standar.Rectangle();
    r_br1.resize(50, 50);
    r_br1.position(250, 25);
    r_br1.attr({
      rect: {
        transform: 'rotate(45)',
      },
    });
    r_br1.attr('root/title', 'branch');
    r_br1.attr('body/fill', 'white');
    r_br1.addTo(graph);

    const r_act_1 = new standar.Rectangle();
    r_act_1.resize(100, 50);
    r_act_1.position(400, 35);
    r_act_1.attr('root/title', 'activity');
    r_act_1.attr('body/fill', 'white');
    r_act_1.addTo(graph);

    const r_act_2 = r_act_1.clone() as joint.shapes.standard.Rectangle;
    r_act_2.position(400, 150);
    r_act_2.addTo(graph);

    const r_br2 = r_br1.clone() as joint.shapes.standard.Rectangle;
    r_br2.position(700, 25);
    r_br2.addTo(graph);

    const c_stop = c_start.clone() as joint.shapes.standard.Circle;
    c_stop.position(800, 50);
    c_stop.attr('root/title', 'stop');
    c_stop.attr('body/fill', 'black');
    c_stop.addTo(graph);

    /*
        Link between shapes
    */

    //  Start to br1
    const l_stb = new joint.dia.Link();
    l_stb.attr('root/title', 'link');
    l_stb.source(c_start);
    l_stb.target(r_br1);
    l_stb.addTo(graph);

    const l_btr1 = new joint.dia.Link();
    l_btr1.source(r_br1);
    l_btr1.target(r_act_1);
    l_btr1.addTo(graph);

    const l_btr2 = new joint.dia.Link();
    l_btr2.source(r_br1);
    l_btr2.target(r_act_2);
    l_btr2.addTo(graph);

    const l_r1tbr2 = new joint.dia.Link();
    l_r1tbr2.source(r_act_1);
    l_r1tbr2.target(r_br2);
    l_r1tbr2.addTo(graph);

    const l_r2tbr2 = new joint.dia.Link();
    l_r2tbr2.source(r_act_2);
    l_r2tbr2.target(r_br2);
    l_r2tbr2.addTo(graph);

    const l_br2te = new joint.dia.Link();
    l_br2te.source(r_br2);
    l_br2te.target(c_stop);
    l_br2te.addTo(graph);

    /* Rect. for view UI */
    /* Object Type */
    const r_object_type = new standar.Rectangle();
    r_object_type.resize(100, 50);
    r_object_type.position(400, 250);
    r_object_type.attr('root/title', 'object_type');
    r_object_type.attr('body/fill', 'white');
    r_object_type.attr('label/text', 'Object Type');
    r_object_type.addTo(graph);

    /* Generator */
    const r_generator = r_object_type.clone() as joint.shapes.standard.Rectangle;
    r_generator.position(100, 100);
    r_generator.resize(40, 40);
    r_generator.attr('body/fill', 'lightblue');
    r_generator.attr('root/title', 'generator');
    r_generator.attr('label/text', 'G');
    r_generator.addTo(graph);

    /* Function */
    /*const r_function = r_object_type.clone() as joint.shapes.standard.Rectangle;
    r_function.position(400,440);
    r_function.attr('root/title','function');
    r_function.attr('label/text','Function');
    r_function.addTo(graph);*/

    let rClickCounter = 0;

    /* General Shape for popup */
    const r_general = r_object_type.clone() as joint.shapes.standard.Rectangle;
    r_general.resize(200, 50);
    r_general.position(400, 320);
    r_general.attr('root/title', 'general');
    r_general.attr('label/text', 'Other Forms');
    r_general.addTo(graph);

    /* Event Handler */
    paper.on({
      'blank:pointerclick': (elementView) => {
        resetAll(this);
      },
      'element:contextmenu': (elementView) => {
        resetAll(this);

        const currentElement = elementView.model;
        currentElement.attr('body/stroke', 'red');
        const currentElementTitle = currentElement.attr('root/title');

        if (currentElementTitle === 'start') {
          $('#start_modal_title').html('<b>' + currentElementTitle + '</b>');
          $('.ui.tiny.start.modal').modal('show');
        } else if (currentElementTitle === 'stop') {
          $('#stop_modal_title').html('<b>' + currentElementTitle + '</b>');
          $('.ui.tiny.stop.modal').modal('show');
        } else if (currentElementTitle === 'activity') {
          $('.ui.tiny.activity.modal').modal('show');
        } else if (currentElementTitle === 'object_type') {
          $('.ui.tiny.object_type.modal').modal('show');
        } else if (currentElementTitle === 'generator') {
          $('.ui.tiny.generator.modal').modal('show');
        } else if (currentElementTitle === 'link') {
          // $('.ui.tiny.function.modal').modal('show');
        } else if (currentElementTitle === 'branch') {
          $('.ui.tiny.branches.modal').modal('show');
        } else if (currentElementTitle === 'general') {
          if (rClickCounter === 0) {
            $('.ui.tiny.queue.modal').modal('show');
          } else if (rClickCounter === 1) {
            $('.ui.tiny.resource.modal').modal('show');
          } else if (rClickCounter === 2) {
            //  $('.ui.tiny.branches.modal').modal('show');
          } else if (rClickCounter === 3) {
            $('.ui.large.sim_config.modal').modal('show');
          } else if (rClickCounter === 4) {
            $('.ui.tiny.sim_control.modal').modal('show');
          } else {
            rClickCounter = -1;
          }
          rClickCounter++;
        }
      },
      'link:contextmenu': (linkView) => {
        const currentLink = linkView.model;

        $('.ui.tiny.function.modal').modal('show');

        rClickCounter++;
      },
    });

    function resetAll(_paper: any) {

      /* Reset all elements in the paper */
      const elements = _paper.model.getElements();
      for (let i = 0, ii = elements.length; i < ii; i++) {
        const currentElement = elements[i];
        currentElement.attr('body/stroke', 'black');
      }

      const links = _paper.model.getLinks();
      for (let j = 0, jj = links.length; j < jj; j++) {
        const currentLink = links[j];
        currentLink.attr('line/stroke', 'black');
        currentLink.label(0, {
          attrs: {
            body: {
              stroke: 'black',
            },
          },
        });
      }

    }
  }
}
</script>
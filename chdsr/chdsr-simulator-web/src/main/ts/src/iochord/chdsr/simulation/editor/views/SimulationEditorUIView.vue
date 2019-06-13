<template>
  <div class="simulation editor test view">
    <div class="ui basic segment">
      <h1>Simulation Editor Test</h1>
      <div id="mypaper"></div>
    </div>

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
</style>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import * as joint from "jointjs";
import "#root/node_modules/jointjs/dist/joint.css";

// Components
import StartNodeModal from "../../../common/kpi/components/modals/StartNodeModal.vue";
import StopNodeModal from "../../../common/kpi/components/modals/StopNodeModal.vue";
import ResourceDataModal from "../../../common/kpi/components/modals/ResourceDataModal.vue";
import QueueDataModal from "../../../common/kpi/components/modals/QueueDataModal.vue";
import ObjectTypeDataModal from "../../../common/kpi/components/modals/ObjectTypeDataModal.vue";
import GeneratorDataModal from "../../../common/kpi/components/modals/GeneratorDataModal.vue";
import FunctionDataModal from "../../../common/kpi/components/modals/FunctionDataModal.vue";
import ControlModal from "../../../common/kpi/components/modals/ControlModal.vue";
import ConfigurationModal from "../../../common/kpi/components/modals/ConfigurationModal.vue";
import BranchNodeModal from "../../../common/kpi/components/modals/BranchNodeModal.vue";
import ActivityNodeModal from "../../../common/kpi/components/modals/ActivityNodeModal.vue";

declare const $: any;

@Component
export default class SimulationEditorUIView extends Vue {
  private selectedGate = "";
  private selectedSJ = "";
  private selectedRule = "";
  private selectedActivityType = "";

  private updated(): void {
    
  }
  private mounted(): void {
    $(".ui.dropdown").dropdown();
    $(".tabular.menu .item").tab();    

    const graph = new joint.dia.Graph();

    const paper = new joint.dia.Paper({
      el: document.getElementById("mypaper"),
      model: graph,
      width: window.innerWidth,
      height: window.innerHeight,
      gridSize: 1
    });

    /* Graph's Elements */
    const standar = joint.shapes.standard;

    const c_start = new standar.Circle();
    c_start.resize(20, 20);
    c_start.position(100, 50);
    c_start.attr("root/title", "start");
    c_start.attr("body/fill", "white");
    c_start.addTo(graph);

    const r_br1 = new standar.Rectangle();
    r_br1.resize(50, 50);
    r_br1.position(250, 25);
    r_br1.attr({
      rect: {
        transform: "rotate(45)"
      }
    });
    r_br1.attr("root/title", "branch");
    r_br1.attr("body/fill", "white");
    r_br1.addTo(graph);

    const r_act_1 = new standar.Rectangle();
    r_act_1.resize(100, 50);
    r_act_1.position(400, 35);
    r_act_1.attr("root/title", "activity");
    r_act_1.attr("body/fill", "white");
    r_act_1.addTo(graph);

    const r_act_2 = r_act_1.clone() as joint.shapes.standard.Rectangle;
    r_act_2.position(400, 150);
    r_act_2.addTo(graph);

    const r_br2 = r_br1.clone() as joint.shapes.standard.Rectangle;
    r_br2.position(700, 25);
    r_br2.addTo(graph);

    const c_stop = c_start.clone() as joint.shapes.standard.Circle;
    c_stop.position(800, 50);
    c_stop.attr("root/title", "stop");
    c_stop.attr("body/fill", "black");
    c_stop.addTo(graph);

    /*
          Link between shapes
      */

    // Start to br1
    const l_stb = new joint.dia.Link();
    l_stb.attr("root/title", "link");
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
    r_object_type.attr("root/title", "object_type");
    r_object_type.attr("body/fill", "white");
    r_object_type.attr("label/text", "Object Type");
    r_object_type.addTo(graph);

    /* Generator */
    const r_generator = r_object_type.clone() as joint.shapes.standard.Rectangle;
    r_generator.position(100, 100);
    r_generator.resize(40, 40);
    r_generator.attr("body/fill", "lightblue");
    r_generator.attr("root/title", "generator");
    r_generator.attr("label/text", "G");
    r_generator.addTo(graph);

    /* Function */
    /*const r_function = r_object_type.clone() as joint.shapes.standard.Rectangle;
      r_function.position(400,440);
      r_function.attr('root/title','function');
      r_function.attr('label/text','Function');
      r_function.addTo(graph);*/

    var rClickCounter = 0;

    /* General Shape for popup */
    const r_general = r_object_type.clone() as joint.shapes.standard.Rectangle;
    r_general.resize(200, 50);
    r_general.position(400, 320);
    r_general.attr("root/title", "general");
    r_general.attr("label/text", "Other Forms");
    r_general.addTo(graph);

    /* Event Handler */
    paper.on({
      "blank:pointerclick": function(elementView) {
        resetAll(this);
      },
      "element:contextmenu": function(elementView) {
        resetAll(this);

        const currentElement = elementView.model;
        currentElement.attr("body/stroke", "red");
        const currentElementTitle = currentElement.attr("root/title");

        if (currentElementTitle == "start") {
          $("#start_modal_title").html("<b>" + currentElementTitle + "</b>");
          $(".ui.tiny.start.modal").modal("show");
        } else if (currentElementTitle == "stop") {
          $("#stop_modal_title").html("<b>" + currentElementTitle + "</b>");
          $(".ui.tiny.stop.modal").modal("show");
        } else if (currentElementTitle == "activity") {
          $(".ui.tiny.activity.modal").modal("show");
        } else if (currentElementTitle == "object_type") {
          $(".ui.tiny.object_type.modal").modal("show");
        } else if (currentElementTitle == "generator") {
          $(".ui.tiny.generator.modal").modal("show");
        } else if (currentElementTitle == "link") {
          //$('.ui.tiny.function.modal').modal('show');
        } else if (currentElementTitle == "branch") {
          $(".ui.tiny.branches.modal").modal("show");
        } else if (currentElementTitle == "general") {
          if (rClickCounter == 0) {
            $(".ui.tiny.queue.modal").modal("show");
          } else if (rClickCounter == 1) {
            $(".ui.tiny.resource.modal").modal("show");
          } else if (rClickCounter == 2) {
            //$('.ui.tiny.branches.modal').modal('show');
          } else if (rClickCounter == 3) {
            $(".ui.large.sim_config.modal").modal("show");
          } else if (rClickCounter == 4) {
            $(".ui.tiny.sim_control.modal").modal("show");
          } else {
            rClickCounter = -1;
          }

          //alert(rClickCounter)
          rClickCounter++;
        }
      },
      "link:contextmenu": function(linkView) {
        const currentLink = linkView.model;

        $(".ui.tiny.function.modal").modal("show");
        //alert(rClickCounter);
        //alert(currentLink.attr('root/title'));

        rClickCounter++;
      }
    });

    function resetAll(paper: any) {
      /* Reset all elements in the paper */
      var elements = paper.model.getElements();
      for (var i = 0, ii = elements.length; i < ii; i++) {
        var currentElement = elements[i];
        currentElement.attr("body/stroke", "black");
      }

      var links = paper.model.getLinks();
      for (var j = 0, jj = links.length; j < jj; j++) {
        var currentLink = links[j];
        currentLink.attr("line/stroke", "black");
        currentLink.label(0, {
          attrs: {
            body: {
              stroke: "black"
            }
          }
        });
      }
    }
  }
}
</script>
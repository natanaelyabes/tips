<!--
  @package ts
  @author N. Y. Wirawan <ny4tips@gmail.com>
  @since 2019
-->
<template>
  <div class="ui small delete modal">
    <i class="close icon"></i>
    <div id="delete_modal_title" class="header">
      <h3 class="ui red header">Delete</h3>
    </div>
    <div class="content">
      <p>Are you sure want to delete selected items?</p>
      <p>This will delete the following node/data, along with affected dependencies:</p>
      <div class="ui accordion">
        <template v-for="(node, i) in nodes">
          <div :key="i" class="title">
            <i class="dropdown icon"></i>
            <div v-if="node.type === 'start'" class="ui green label">{{ node.type }}</div>
            <div v-else-if="node.type === 'stop'" class="ui red label">{{ node.type }}</div>
            <div v-else class="ui blue label">{{ node.type }}</div>
            <div class="ui label">{{ node.id }}</div>
            <div v-if="node.label" class="ui basic label">{{ node.label }}</div>
          </div>
          <div v-if="node.category === 'data'" :key="i + 100" class="content">
            This data node is being used by:

            <div class="ui warning message">
              <ul class="list">
                <li :key="j" v-for="(d, j) in node.dependedNodes">
                  <div v-if="d.type === 'start'" class="ui green label">{{ d.type }}</div>
                  <div v-else-if="d.type === 'stop'" class="ui red label">{{ d.type }}</div>
                  <div v-else class="ui blue label">{{ d.type }}</div>
                  <div class="ui label">{{ d.id }}</div>
                  <div v-if="d.label" class="ui basic label">{{ d.label }}</div>
                </li>
              </ul>
            </div>

          </div>
          <div v-else :key="i + 100" class="content">
            <div class="ui positive message">Node has no cascading dependencies.</div>
          </div>
        </template>
      </div>
    </div>
    <div class="actions">
      <div @click = "resetFields()" class="ui labeled icon red approve button">
        <i class="trash alternate outline icon"></i>
        Delete
      </div>
      <div class="ui cancel button">Cancel</div>
    </div>
  </div>
</template>

<style>

</style>

<script lang="ts">
// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Interfaces
import { DeletableModal } from '../../interfaces/DeletableModal';

// Classes
import GraphModule from '@/iochord/ips/common/graphs/ism/stores/GraphModule';
import SemanticComponent from '@/iochord/ips/common/ui/semantic-components/SemanticComponent';
import { JointGraphPageImpl } from '../../../../common/graphs/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';
import { TSMap } from 'typescript-map';
import { GraphPage } from '@/iochord/ips/common/graphs/ism/interfaces/GraphPage';
import { GraphStartEventNodeImpl } from '../../../../common/graphs/ism/class/components/GraphStartEventNodeImpl';
import { GraphActivityNodeImpl } from '../../../../common/graphs/ism/class/components/GraphActivityNodeImpl';
import { GraphDataGeneratorImpl } from '../../../../common/graphs/ism/class/components/GraphDataGeneratorImpl';
import { GraphDataObjectTypeImpl } from '../../../../common/graphs/ism/class/components/GraphDataObjectTypeImpl';
import { GraphDataFunctionImpl } from '../../../../common/graphs/ism/class/components/GraphDataFunctionImpl';
import { GraphDataResourceImpl } from '@/iochord/ips/common/graphs/ism/class/components/GraphDataResourceImpl';
import GraphSubject from '@/iochord/ips/common/graphs/ism/rxjs/GraphSubject';

// Vuex
const graphModule = getModule(GraphModule);

// JQuery handler
declare const $: any;

@Component({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})

/**
 * The delete modal.
 *
 * @export
 * @class DeleteModal
 * @extends {SemanticComponent}
 * @implements {DeletableModal}
 *
 * @package ts
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2019
 */
export default class DeleteModal extends SemanticComponent implements DeletableModal {

  /**
   * List of the deleted nodes.
   *
   * @private
   * @type {any[]}
   * @memberof DeleteModal
   */
  private nodes: any[] = [];

  /**
   * The page of the graph.
   *
   * @private
   * @type {JointGraphPageImpl}
   * @memberof DeleteModal
   */
  private page?: JointGraphPageImpl;

  /**
   * Put the nodes and its depended nodes to the list of deleted nodes.
   *
   * @param {*} node
   * @memberof DeleteModal
   */
  public populateNode(node: any): void {
    node.dependedNodes = this.getDependedNodes(node);
    this.nodes.push(node);
  }

  /**
   * Reset the modal fields.
   *
   * @memberof DeleteModal
   */
  public resetFields(): void {
    this.nodes.forEach((data) => {
      const pageId = (data.id as string).split('-')[0];

      graphModule.graph.getPages()!.get(pageId).getData()!.forEach((datum) => {
        switch (datum.getType()) {
          case 'generator':
            const generator = (datum as GraphDataGeneratorImpl);

            if ((generator.getObjectTypeRef() as string) === data.id) {
              generator.setObjectTypeRef('');
              $('#generator_txt_type').dropdown('clear');
            }

            break;

          case 'objecttype':
            const objecttype = (datum as GraphDataObjectTypeImpl);

            if ((objecttype.getTypeRefs() as string) === data.id) {
              objecttype.setTypeRefs('');
              $('#x_txt_table').dropdown('clear');
            }

            break;

          case 'function':
            const fx = (datum as GraphDataFunctionImpl);

            fx.getInputParametersRefs()!.forEach((ot) => {
              if ((ot as string) === data.id) {
                fx.getInputParametersRefs()!.delete(ot);
              }
            });

            fx.getOutputVariablesRefs()!.forEach((ot) => {
              if ((ot as string) === data.id) {
                fx.getOutputVariablesRefs()!.delete(ot);
              }
            });

            break;

          case 'resource':
            const resource = (datum as GraphDataResourceImpl);

            if (resource.getDataRef() === data.id) {
              resource.setDataRef('');
              $('#x_txt_table').dropdown('clear');
            }

            break;
        }
      });

      graphModule.graph.getPages()!.get(pageId).getNodes()!.forEach((node) => {
        switch (node.getType()) {
          case 'start':
            const start = (node as GraphStartEventNodeImpl);

            if ((start.getGeneratorRef() as string) === data.id) {
              start.setGeneratorRef('');
              $('#start_txtgen').dropdown('clear');
            }

            break;
          case 'activity':
            const activity = (node as GraphActivityNodeImpl);

            if ((activity.getQueueRef() as string) === data.id) {
              activity.setQueueRef('');
              $('#act_txtqueuelabel').dropdown('clear');
            }

            if ((activity.getResourceRef() as string) === data.id) {
              activity.setResourceRef('');
              $('#act_txtresource').dropdown('clear');
            }

            if ((activity.getFunctionRef() as string) === data.id) {
              activity.setFunctionRef('');
              $('#act_txtfunction').dropdown('clear');
            }

            break;
        }
      });
    });

    GraphSubject.update(graphModule.graph);

  }

  /**
   * Reset the list of the deleted nodes.
   *
   * @memberof DeleteModal
   */
  public reset(): void {
    this.nodes = [];
  }

  /**
   * Vue deleted lifecycle
   *
   * @override
   * @memberof DeleteModal
   */
  public destroyed(): void {
    this.nodes = [];
  }

  /**
   * Returns the depended nodes of the deleted node.
   *
   * @param {*} data
   * @returns
   * @memberof DeleteModal
   */
  public getDependedNodes(data: any) {
    const dependedNodes: any[] = [];

    const pageId = (data.id as string).split('-')[0];

    graphModule.graph.getPages()!.get(pageId).getData()!.forEach((datum) => {
      switch (datum.getType()) {
        case 'generator':
          const generator = (datum as GraphDataGeneratorImpl);

          if ((generator.getObjectTypeRef() as string) === data.id) {
            dependedNodes.push({
              id: generator.getId(),
              label: generator.getLabel(),
              type: generator.getType(),
              category: 'data',
            });
          }

          break;

        case 'objecttype':
          const objecttype = (datum as GraphDataObjectTypeImpl);

          if ((objecttype.getTypeRefs() as string) === data.id) {
            dependedNodes.push({
              id: objecttype.getId(),
              label: objecttype.getLabel(),
              type: objecttype.getType(),
              category: 'data',
            });
          }

          break;

        case 'function':
          const fx = (datum as GraphDataFunctionImpl);

          fx.getInputParametersRefs()!.forEach((ot) => {
            if ((ot as string) === data.id) {
              dependedNodes.push({
                id: fx.getId(),
                label: fx.getLabel(),
                type: fx.getType(),
                category: 'data',
              });
            }
          });

          fx.getOutputVariablesRefs()!.forEach((ot) => {
            if ((ot as string) === data.id) {
              dependedNodes.push({
                id: fx.getId(),
                label: fx.getLabel(),
                type: fx.getType(),
                category: 'data',
              });
            }
          });

          break;

        case 'resource':
          const resource = (datum as GraphDataResourceImpl);

          if (resource.getDataRef() === data.id) {
            dependedNodes.push({
              id: resource.getId(),
              label: resource.getLabel(),
              type: resource.getType(),
              category: 'data',
            });
          }

          break;
      }
    });

    graphModule.graph.getPages()!.get(pageId).getNodes()!.forEach((node) => {
      switch (node.getType()) {
        case 'start':
          const start = (node as GraphStartEventNodeImpl);

          if ((start.getGeneratorRef() as string) === data.id) {
            dependedNodes.push({
              id: start.getId(),
              label: start.getLabel(),
              type: start.getType(),
              category: 'node',
            });
          }

          break;
        case 'activity':
          const activity = (node as GraphActivityNodeImpl);

          if ((activity.getQueueRef() as string) === data.id) {
            dependedNodes.push({
              id: activity.getId(),
              label: activity.getLabel(),
              type: activity.getType(),
              category: 'node',
            });
          }

          if ((activity.getResourceRef() as string) === data.id) {
            dependedNodes.push({
              id: activity.getId(),
              label: activity.getLabel(),
              type: activity.getType(),
              category: 'node',
            });
          }

          if ((activity.getFunctionRef() as string) === data.id) {
            dependedNodes.push({
              id: activity.getId(),
              label: activity.getLabel(),
              type: activity.getType(),
              category: 'node',
            });
          }

          break;
      }
    });

    return dependedNodes;
  }

  /**
   * Declare semantic modules.
   *
   * @memberof DeleteModal
   */
  public declareSemanticModules(): void {
    $('.ui.accordion').accordion();
  }
}
</script>

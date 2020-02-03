// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/ips/common/ui/layout/class/BaseComponent';
import { JointGraphDataImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphDataImpl';
import { GraphDataImpl } from '@/iochord/ips/common/graph/ism/class/GraphDataImpl';
import { JointGraphPageImpl } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/class/JointGraphPageImpl';

// Interfaces
import { GraphData } from '@/iochord/ips/common/graph/ism/interfaces/GraphData';

// Vuex & Rxjs
/** Graph */
import GraphModule from '@/iochord/ips/common/graph/ism/stores/GraphModule';
import GraphSubject from '@/iochord/ips/common/graph/ism/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';


// Enums
import { DATA_TYPE } from '@/iochord/ips/common/graph/ism/rendering-engine/joint/shapes/enums/DATA';
import * as DATA_ENUMS from '@/iochord/ips/common/graph/ism/enums/DATA';
import * as DATA_FACTORY from '@/iochord/ips/common/graph/ism/enums/DATA';


// Enums of DATA
enum DATA {
  datatable = 'datatable',
  generator = 'generator',
  objecttype = 'objecttype',
  function = 'function',
  resource = 'resource',
  queue = 'queue',
}

// Fetch module from stores
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component<DataMixin>({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
/**
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export default class DataMixin extends BaseComponent {

  public newData?: JointGraphDataImpl;

  public createData(type: DATA, e: MouseEvent) {

    /** Local variable initialization */
    const keys: any = {
      elementType: 'elementType',
    };

    /** Create new item */
    this.newData = new JointGraphDataImpl();

    /** Set properties for the newly created item */
    this.newData.setId(`0-${type}-${GraphDataImpl.instance.size()}`);
    this.newData.setType(type.toString());
    this.newData.setSize((DATA_TYPE as any)[type].size);
    this.newData.setMarkup((DATA_TYPE as any)[type].markup);
    this.newData.setAttr((DATA_TYPE as any)[type].attr);
    this.newData.setImageIcon((DATA_TYPE as any)[type].image);

    (this.newData as JointGraphDataImpl).setLabel(`New Data ${GraphDataImpl.instance.size()}`);

    /** Put new item in vuex store */
    graphModule.setNewItem(this.newData as JointGraphDataImpl);

    /** Set dragging state to true */
    editorState.setDragging(true);
  }

  public moveData(e: MouseEvent, activePage: JointGraphPageImpl) {
    if (editorState.dragging && graphModule.newItem !== null) {

      /** Capture svgPoint from MouseEvent */
      const svgPoint = (activePage.getPaper().svg as SVGSVGElement).createSVGPoint();
      svgPoint.x = e.offsetX;
      svgPoint.y = e.offsetY;

      /** Transform svgPoint to joint.js paper matrix */
      const pointTransformed = svgPoint.matrixTransform(activePage.getPaper().viewport.getCTM()!.inverse());

      /** Set position according to the transformed point captured from MouseEvent */
      (graphModule.newItem as JointGraphDataImpl).setPosition({
        x: pointTransformed.x,
        y: pointTransformed.y,
      });

      /** Render newItem */
      (graphModule.newItem as JointGraphDataImpl).render(activePage.getGraph());

      /** Listen to keydown event to check esc button */
      window.addEventListener('keydown', this.cancelCreateData);
    }
  }

  public cancelCreateData(e: KeyboardEvent) {
    if (e.key === 'Escape') {
      if (editorState.dragging && (graphModule.newItem)) {

        // Set dragging state to false
        editorState.setDragging(false);

        // Remove data from joint.js canvas
        (graphModule.newItem as JointGraphDataImpl).getData().remove();

        // Remove data from GraphModule temporary container
        graphModule.setNewItem(null);

        // Enable the toolbar again
        $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Canceling data creation.`,
          newestOnTop: true,
        });

        // Remove event listener for cancelCreateItem
        window.removeEventListener('keydown', this.cancelCreateData);
      }
    }
  }

  public deleteData(activePage: JointGraphPageImpl, cell: joint.dia.Element) {

    // Check if cell is a node object
    if (cell.isElement()) {
      const dataId = cell.attributes.dataId;
      const datum = graphModule.pageDatum(activePage as JointGraphPageImpl, dataId) as GraphData;

      // If Node exists
      if (datum) {
        graphModule.deletePageDatum({
          page: activePage as JointGraphPageImpl,
          datum,
        });

        // Update local instance
        GraphDataImpl.instance.delete(dataId as string);

        // Update the rxjs observable
        GraphSubject.update(graphModule.graph);

        // Pop up toast
        ($('body') as any).toast({
          position: 'bottom right',
          class: 'info',
          className: {
            toast: 'ui message',
          },
          message: `Successfully remove a data`,
          newestOnTop: true,
        });
      }
    }
  }

  public saveData(e: MouseEvent, activePage: JointGraphPageImpl) {
    editorState.setDragging(false);
    if (graphModule.newItem !== null) {

      /** Render newItem */
      (graphModule.newItem as JointGraphDataImpl).render(activePage.getGraph());

      /** Construct newItem according to its type */
      const type = graphModule.newItem.getType() as string;
      const newItem = new (DATA_FACTORY.DATA_TYPE as any)[type]();
      newItem.setId(`0-${type}-${GraphDataImpl.instance.size()}`);
      newItem.setType(type);

      (newItem).setLabel(`New Data ${GraphDataImpl.instance.size()}`);

      // Add data to Vuex GraphModule
      graphModule.addPageDatum(
        {
          page: activePage,
          datum: newItem as GraphData,
        },
      );

      // Update local instance
      GraphDataImpl.instance.set(newItem.getId() as string, (DATA_ENUMS.DATA_TYPE as any)[type].deserialize(newItem));

      // Update the rxjs observable
      GraphSubject.update(graphModule.graph);

      // Set container to null
      graphModule.setNewItem(null);
      this.newData = undefined;

      // Pop up toast
      const icon = {
        start: 'green play',
        stop: 'red circle',
        activity: 'blue square',
        branch: 'blue random',
      };

      ($('body') as any).toast({
        position: 'bottom right',
        class: 'success',
        className: {
          toast: 'ui message',
        },
        showIcon: (icon as any)[type],
        message: `${(newItem as GraphData).getId()} has been created.`,
        newestOnTop: true,
      });

      // Enable the toolbar again
      $('.sidebar.component .ui.basic.button.item').removeClass('disabled');

      // Remove event listener for cancelCreateItem
      window.removeEventListener('keydown', this.cancelCreateData);
    }
  }
}

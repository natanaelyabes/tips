// Vue & Libraries
import { Component } from 'vue-property-decorator';
import { getModule } from 'vuex-module-decorators';

// Classes
import BaseComponent from '@/iochord/chdsr/common/ui/layout/classes/BaseComponent';


// Vuex & Rxjs

/** Graph */
import GraphModule from '@/iochord/chdsr/common/graph/sbpnet/stores/GraphModule';
import GraphSubject from '@/iochord/chdsr/common/graph/sbpnet/rxjs/GraphSubject';

/** Graph Editor */
import EditorState from '../../../stores/editors/EditorState';

// Fetch module from stores
const graphModule = getModule(GraphModule);
const editorState = getModule(EditorState);

@Component({
  subscriptions: () => {
    return (
      {
        graph: GraphSubject.toObservable(),
      }
    );
  },
})
export default class ConnectorMixin extends BaseComponent {

}

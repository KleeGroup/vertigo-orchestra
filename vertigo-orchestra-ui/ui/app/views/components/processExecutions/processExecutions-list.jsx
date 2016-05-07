import React, {Component, PropTypes} from 'react';
import {component as SmartList} from 'focus-components/page/list';
import  {component as ListComponent} from 'focus-components/list/timeline/list'
import ProcessExecutionsLine from './processExecutions-line';

import {listProcessExecutionsActions} from '../../../action/process-executions';


export default React.createClass({
  displayName: 'ProcessExecutionsList',
  action: undefined,
  propTypes: {
    id: PropTypes.number.isRequired,
    status: PropTypes.string,
    LineComponent: PropTypes.element,
    action: PropTypes.func,
    columns: PropTypes.array,
    handleLineClick: PropTypes.func,
    store: PropTypes.object
  },

  componentWillMount() {
    this.action=listProcessExecutionsActions(this.props.id, this.props.status);
  },

  componentWillUpdate(nextProps, nextState) {
      this.action.updateProperties({criteria : {status:nextProps.status, id:nextProps.id}});
  },

  shouldComponentUpdate: function(nextProps, nextState) {
    let differentId = nextProps.id !== this.props.id;
    let differentStatus = nextProps.status !== this.props.status;
    return differentId || differentStatus;
  },

  render() {
    const {handleLineClick, store, columns} = this.props;
    return (
        <SmartList
            ListComponent={ListComponent}
            LineComponent={ProcessExecutionsLine}
            action={{load: this.action.load}}
            columns={columns}
            onLineClick={handleLineClick}
            store={store}
            isSelection={false}
            isManualFetch={true}
        />
      );
    }
});

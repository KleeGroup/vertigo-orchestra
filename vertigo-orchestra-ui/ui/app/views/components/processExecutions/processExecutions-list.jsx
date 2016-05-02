import React, {Component, PropTypes} from 'react';
import {component as SmartList} from 'focus-components/page/list';
import  {component as ListComponent} from 'focus-components/list/selection/list'
import ProcessExecutionsLine from './processExecutions-line';

const propTypes = {
    id: PropTypes.number.isRequired,
    LineComponent: PropTypes.element,
    action: PropTypes.func,
    columns: PropTypes.array,
    handleLineClick: PropTypes.func,
    store: PropTypes.object
};

function ProcessExecutionsList({handleLineClick, action, store, columns}) {
    return (
        <SmartList
            ListComponent={ListComponent}
            LineComponent={ProcessExecutionsLine}
            action={{load: action}}
            columns={columns}
            onLineClick={handleLineClick}
            store={store}
            isSelection={false}
        />
    );
};

ProcessExecutionsList.displayName = 'ProcessExecutionsList';
ProcessExecutionsList.propTypes = propTypes;
export default ProcessExecutionsList;

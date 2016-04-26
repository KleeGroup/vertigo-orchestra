import React, {Component, PropTypes} from 'react';
import {component as SmartList} from 'focus-components/page/list';
import  {component as ListComponent} from 'focus-components/list/selection/list'
import SummaryLine from './summary-card-line';

const propTypes = {
    LineComponent: PropTypes.element,
    action: PropTypes.func,
    columns: PropTypes.array,
    handleLineClick: PropTypes.func,
    store: PropTypes.object
};

function SummaryList({handleLineClick, action, store, columns}) {
    return (
        <SmartList
            ListComponent={ListComponent}
            LineComponent={SummaryLine}
            action={{load: action}}
            columns={columns}
            onLineClick={handleLineClick}
            store={store}
            isSelection={false}
        />
    );
};

SummaryList.displayName = 'SummaryList';
SummaryList.propTypes = propTypes;
export default SummaryList;

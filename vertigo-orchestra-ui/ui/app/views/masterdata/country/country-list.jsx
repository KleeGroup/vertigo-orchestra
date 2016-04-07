import React, {Component, PropTypes} from 'react';
import {component as SmartList} from 'focus-components/page/list';
import  {component as ListComponent} from 'focus-components/list/selection/list'
import CountryLine from './country-line';

const propTypes = {
    LineComponent: PropTypes.element,
    action: PropTypes.func,
    columns: PropTypes.array,
    handleLineClick: PropTypes.func.isRequired,
    store: PropTypes.object
};

function CountryList({handleLineClick, action, store, columns}) {
    return (
        <SmartList
            ListComponent={ListComponent}
            LineComponent={CountryLine}
            action={{load: action}}
            columns={columns}
            onLineClick={handleLineClick}
            store={store}
            isSelection={false}
        />
    );
};

CountryList.displayName = 'CountryList';
CountryList.propTypes = propTypes;
export default CountryList;

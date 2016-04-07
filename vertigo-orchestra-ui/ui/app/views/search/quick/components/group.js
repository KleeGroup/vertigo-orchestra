// libraires
import React, {PropTypes, Component} from 'react';
import Translation from 'focus-components/behaviours/translation';
import formatter from  'focus-core/definition/formatter/number';
import history from 'focus-core/history';
import {quickSearchStore} from 'focus-core/search/built-in-store';
import dispatcher from 'focus-core/dispatcher';

//web components
import {component as Button} from 'focus-components/common/button/action';

const propTypes = {
    count: PropTypes.number.isRequired,
    groupKey: PropTypes.string.isRequired,
    showAllHandler: PropTypes.func
};

const defaultProps = {
    count: 0
};

@Translation
class QuickSearchGroup extends Component {

    showAllClickHandler() {
        const {groupKey, showAllHandler} = this.props;
        const query = quickSearchStore.getQuery();
        const scope = quickSearchStore.getScope();
        //dispatch in advanced search store
        dispatcher.handleViewAction({
            data: {
                query,
                scope,
                groupingKey: null,
                results: [],
                facets: [],
                totalCount: 0
            },
            type: 'update',
            identifier: 'ADVANCED_SEARCH'
        });
        if(showAllHandler){
            showAllHandler();
        }
        history.navigate('#search/advanced', true);
        window.scrollTo(0, 0);
    }

    render() {
        const {children, count, groupKey, showAllHandler} = this.props
        return (
            <div data-focus="group-container">
                <div data-focus='group-container-title'>
                    <div>
                        <h3>
                            <span>{groupKey}</span>
                            <span>{formatter.format(count)}</span>
                        </h3>
                        <p>{this.i18n('search.mostRelevant')}</p>
                    </div>
                    <div data-focus='group-container-actions__right'>
                        <Button shape={null} color='accent' handleOnClick={this.showAllClickHandler} label={this.i18n('search.show.all')} />
                    </div>
                </div>
                <div data-focus="group-container-results">
                    {children}
                </div>
            </div>
        );
    }
}

QuickSearchGroup.propTypes = propTypes;
QuickSearchGroup.defaultProps = defaultProps;
QuickSearchGroup.displayName = 'QuickSearchGroup';

export default QuickSearchGroup;

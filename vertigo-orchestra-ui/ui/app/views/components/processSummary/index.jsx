import React, {PropTypes} from 'react';

const propTypes = {
    errorsCount: PropTypes.number,
    successfulCount: PropTypes.number,
    misfiredCount: PropTypes.number,
    averageExecutionTime: PropTypes.number,
    handleErrorClick: PropTypes.func,
    handleSuccessClick: PropTypes.func
};

function ProcessSummary({ errorsCount, successfulCount, misfiredCount, averageExecutionTime, handleErrorClick, handleSuccessClick}) {
    return (
          <div  data-orchestra='execution-summary'>
            <div data-orchestra='execution-summary-counts'>
              <div>
                <div className='errorsCount' onClick={handleErrorClick} >
                  <span>{errorsCount}</span>
                </div>
                <div className='successfulCount'onClick={handleSuccessClick} >
                  <span>{successfulCount}</span>
                </div>
                <div className='misfiredCount' >
                  <span>{misfiredCount}</span>
                </div>
              </div>
            </div>
            <div data-orchestra='execution-summary-average'>
              <i className="material-icons">timer</i>
              <span>{averageExecutionTime}s</span>
            </div>
          </div>
    );
};

ProcessSummary.displayName = 'ProcessSummary';
ProcessSummary.propTypes = propTypes;
export default ProcessSummary;

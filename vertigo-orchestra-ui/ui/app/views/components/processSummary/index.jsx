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
                <div data-orchestra='execution-summary-icon' className='errorsCount' onClick={handleErrorClick} >
                  <i className="material-icons">report_problem</i><br/>
                  <span>{errorsCount}</span>
                </div>
                <div data-orchestra='execution-summary-icon' className='successfulCount' onClick={handleSuccessClick} >
                  <i className="material-icons">verified_user</i><br/>
                  <span>{successfulCount}</span>
                </div>
                <div data-orchestra='execution-summary-icon' className='misfiredCount' >
                  <i className="material-icons">alarm_off</i><br/>
                  <span>{misfiredCount}</span>
                </div>
                <div data-orchestra='execution-summary-icon'>
                  <i className="material-icons">timer</i><br/>
                  <span>{averageExecutionTime}s</span>
                </div>
          </div>
    );
};

ProcessSummary.displayName = 'ProcessSummary';
ProcessSummary.propTypes = propTypes;
export default ProcessSummary;

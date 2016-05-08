import React, {PropTypes} from 'react';

const propTypes = {
    errorsCount: PropTypes.number,
    successfulCount: PropTypes.number,
    misfireCount: PropTypes.number,
    averageExecutionTime: PropTypes.number,
    handleErrorClick: PropTypes.func,
    handleSuccessClick: PropTypes.func
};

function ProcessSummary({ errorsCount, successfulCount, misfireCount, averageExecutionTime, handleErrorClick, handleSuccessClick}) {
    return (
            <div>
              <div onClick={handleErrorClick} >
                {errorsCount}
              </div>
              <div onClick={handleSuccessClick} >
                {successfulCount}
              </div>
              <div>
                {averageExecutionTime}
              </div>
            </div>
    );
};

ProcessSummary.displayName = 'ProcessSummary';
ProcessSummary.propTypes = propTypes;
export default ProcessSummary;

package io.vertigo.orchestra.boot.initializer;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.tempo.job.JobManager;

import javax.inject.Inject;

import org.apache.log4j.Logger;

/**
 * Initialisation du manager des jobs.
 *
 * @author jmforhan
 */
public final class JobManagerInitializer implements ComponentInitializer<JobManager> {

    private static final Logger LOG = Logger.getLogger(JobManagerInitializer.class.getName());

    /**
     * Construit une instance de JobManagerInitializer.
     */
    @Inject
    public JobManagerInitializer() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public void init(final JobManager manager) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Initialisation des jobs");
        }
        // manager.scheduleEveryDayAtHour(RepportJob.getJobDefinition(), 9);
    }
}

package toma.meteo.batch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport{
	
	Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);
	
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.debug("Batch terminé");
		} else {
			logger.debug("Statut du batch: " + jobExecution.getStatus());
		}
		logger.debug("*********************************************************");
		logger.debug("*****               Fin de batch                    *****");
		logger.debug("*****                                               *****");
		logger.debug("*********************************************************");
	}

}

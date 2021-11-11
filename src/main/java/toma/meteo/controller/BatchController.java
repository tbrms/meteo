package toma.meteo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import toma.meteo.batch.BatchExportConfig;
import toma.meteo.bean.BulletinMeteoExt;

@RestController
public class BatchController {
	
	@Autowired
    JobLauncher jobLauncher;
	
	@Autowired
    JobOperator jobOperator;

//    @Autowired
//    Job job;
    
    /**
	 * Recuperer les n derniers bulletins meteo exterieure en BDD
	 */
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "Landeur de batch", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Job lancé"),
			@ApiResponse(code = 500, message = "Erreur interne du serveur") })
	@GetMapping(value = "/batch/start/{nom}", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String startJob(@PathVariable("nom") String nom) {
		
		try {
			        
			Long id = jobOperator.start(nom, LocalDateTime.now().toString());
			
			return "Identifiant du job: " + id;
	        		      
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
	}
    
    @GetMapping(value = "/jobLauncher", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String jobLauncher() throws Exception{
//    	JobParameters jobParameters = new JobParametersBuilder()
//    			.addLong("time", System.currentTimeMillis()).toJobParameters();
//        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
//        
//        return "Code: " + jobExecution.getExitStatus().getExitCode()
//        		+ " - Status: " + jobExecution.getExitStatus().getExitDescription()
//        		+ " - Duree: " + (jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime());
//        
        long id = jobOperator.start(BatchExportConfig.JOB_NAME, LocalDateTime.now().toString());
        return 	"Identifiant du job: " + id;
      
    }
    
    
    
    @GetMapping(value = "/stop", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String stop() throws Exception{
   
    	Set<Long> executions = jobOperator.getRunningExecutions("exportJob");
    	jobOperator.stop(executions.iterator().next());
        return "";
      
    }

}

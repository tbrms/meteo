package toma.meteo.batch;

import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import toma.meteo.bean.BulletinMeteoExt;

//@Configuration
//@EnableBatchProcessing
public class BatchBoucleConfig {

	private static final String JOB_NAME = "boucleJob";
	private static final String STEP_NAME = "processingBoucleStep";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private Resource outputResource = 
			new FileSystemResource("output/date.csv");

	@Qualifier(STEP_NAME)
	@Bean
	public Step boucleStep() throws Exception {
		return stepBuilderFactory
				.get(STEP_NAME)
				.<Date, Date>chunk(5)				
				.reader(boucleReader())
				.processor(boucleProcessor())
				.writer(boucleWriter())
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.allowStartIfComplete(true)
				.build();
	}

	@Qualifier(JOB_NAME)
	@Bean
	public Job boucleJob(JobCompletionNotificationListener listener) throws Exception {
		return jobBuilderFactory
				.get(JOB_NAME)
				.incrementer(new SampleIncrementer())
				.start(boucleStep())
				.listener(listener)
				.build();
	}
	
	@Bean
    public ItemProcessor<Date,Date> boucleProcessor() {
        return new BoucleProcessor();
    }
	
	@Bean
    public ItemReader<Date> boucleReader() throws Exception {
        JpaPagingItemReader<Date> reader = new JpaPagingItemReader<Date>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("select current_time;");
        reader.setPageSize(5);
        reader.afterPropertiesSet();

        return reader;
    }
	
	@Bean
    public FlatFileItemWriter<Date> boucleWriter() {
        //Create writer instance
        FlatFileItemWriter<Date> writer = new FlatFileItemWriter<>();
         
        //Set output file location
        writer.setResource(outputResource);
         
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);
         
        //Name field values sequence based on object properties 
        writer.setLineAggregator(new DelimitedLineAggregator<Date>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Date>() {
                    {
                        setNames(new String[] { "id", "date", "temperature", "pression", "humidite" });
                    }
                });
            }
        });
        return writer;
    }
}

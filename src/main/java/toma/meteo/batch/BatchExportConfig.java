package toma.meteo.batch;

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

@Configuration
@EnableBatchProcessing
public class BatchExportConfig {
	
	public static final String JOB_NAME = "exportJob";
	public static final String STEP_NAME = "processingExportStep";

	

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	private Resource outputResource = 
			new FileSystemResource("output/outputData.csv");

	@Qualifier(STEP_NAME)
	@Bean
	public Step exportStep() throws Exception {
		return stepBuilderFactory
				.get(STEP_NAME)
				.<BulletinMeteoExt, BulletinMeteoExt>chunk(5)				
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.taskExecutor(new SimpleAsyncTaskExecutor())
				.allowStartIfComplete(true)
				.build();
	}

	@Qualifier(JOB_NAME)
	@Bean
	public Job exportJob(JobCompletionNotificationListener listener) throws Exception {
		return jobBuilderFactory
				.get(JOB_NAME)
				.incrementer(new SampleIncrementer())
				.start(exportStep())
				.listener(listener)
				.build();
	}
	
	@Bean
    public ItemProcessor<BulletinMeteoExt,BulletinMeteoExt> itemProcessor() {
        return new ExportCsvBulletinMeteoExtProcessor();
    }
	
	@Bean
    public ItemReader<BulletinMeteoExt> itemReader() throws Exception {
        JpaPagingItemReader<BulletinMeteoExt> reader = 
        		new JpaPagingItemReader<BulletinMeteoExt>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT bm FROM BulletinMeteoExt bm ORDER BY bm.id DESC");

        reader.setPageSize(5);
        reader.afterPropertiesSet();

        return reader;
    }
	
	@Bean
    public FlatFileItemWriter<BulletinMeteoExt> itemWriter() 
    {
        //Create writer instance
        FlatFileItemWriter<BulletinMeteoExt> writer = new FlatFileItemWriter<>();
         
        //Set output file location
        writer.setResource(outputResource);
         
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);
         
        //Name field values sequence based on object properties 
        writer.setLineAggregator(new DelimitedLineAggregator<BulletinMeteoExt>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<BulletinMeteoExt>() {
                    {
                        setNames(new String[] { "id", "date", "temperature", "pression", "humidite" });
                    }
                });
            }
        });
        return writer;
    }
}

package br.com.luisjustin.api.mobilidade.config;

import br.com.luisjustin.api.mobilidade.jobs.DataPoaApiCheckJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobScheduler {

    @Bean
    public JobDetail checkApiJobDetail() {
        return JobBuilder.newJob(DataPoaApiCheckJob.class).withIdentity("DataPOPCheckAPI")
                .storeDurably().build();
    }

    @Bean
    public Trigger checkApiJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(1).repeatForever();

        return TriggerBuilder.newTrigger().forJob(checkApiJobDetail())
                .withIdentity("DataPoaCheckApiTrigger").withSchedule(scheduleBuilder).build();
    }

}

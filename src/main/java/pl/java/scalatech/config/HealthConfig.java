package pl.java.scalatech.config;

import java.util.Random;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;

@Configuration
public class HealthConfig {
    @Bean
    HealthIndicator myHealthIndicator(){
        return () -> {
            if(new Random().nextBoolean()){
                return Health.up().status("ok").build();
            }
            return Health.down().status("problem").withDetail("my", "przoodwnik").build();
        };
        
    }
    
    @Bean
    @ExportMetricWriter
    MetricWriter jmxMetric(MBeanExporter exporter){
        return new JmxMetricWriter(exporter);
    }
    
}

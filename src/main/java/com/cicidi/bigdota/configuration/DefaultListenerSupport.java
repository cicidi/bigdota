package com.cicidi.bigdota.configuration;

import com.cicidi.bigdota.cassandra.repo.FailedRepository;
import com.cicidi.bigdota.domain.dota.Download_failed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.RetryListenerSupport;

public class DefaultListenerSupport extends RetryListenerSupport {
    private final static Logger logger = LoggerFactory.getLogger(DefaultListenerSupport.class);

    @Override
    public <T, E extends Throwable> void close(RetryContext context,
                                               RetryCallback<T, E> callback, Throwable throwable) {
        logger.info("onClose");
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class, CassandraConfig.class, JobConfig.class);
        FailedRepository failedRepository = applicationContext.getBean(FailedRepository.class);
        failedRepository.save(new Download_failed("333"));
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context,
                                                 RetryCallback<T, E> callback, Throwable throwable) {
        logger.info("onError");
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context,
                                                 RetryCallback<T, E> callback) {
        logger.info("onOpen");
        return super.open(context, callback);
    }
}
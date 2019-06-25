package com.stackroute.serviceproviderprofileservice.service;

import com.stackroute.serviceproviderprofileservice.domain.ServiceProvider;
import com.stackroute.serviceproviderprofileservice.repository.ServiceProviderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService
{
    ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${javainuse.rabbitmq.exchange}")
    String exchange;

    @Value("${javainuse.rabbitmq.routingkey}")
    private String routingkey;


    @Autowired
    public ServiceProviderServiceImpl(ServiceProviderRepository serviceProviderRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @Override
    public ServiceProvider saveServiceProvider(ServiceProvider serviceProvider) {
        return serviceProviderRepository.save(serviceProvider);
    }






    public void send(ServiceProvider serviceProvider) {
        rabbitTemplate.convertAndSend(exchange, routingkey, serviceProvider);
        System.out.println("Send msg = " + serviceProvider);
    }
}

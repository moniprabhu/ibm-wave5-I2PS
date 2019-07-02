package com.stackroute.intelligentservice.service;

import com.stackroute.intelligentservice.domain.IntelligentService;
import com.stackroute.intelligentservice.repository.IntelligentServiceRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class IntelligentServiceImpl implements IntelligentSeviceInterface {


    @Autowired
    IntelligentServiceRepository intelligentServiceRepository;

    @Override
    public IntelligentService create(IntelligentService intelligentService) {
       return intelligentServiceRepository.save(intelligentService);
    }

    @Override
    public IntelligentService update(String role) {
        return null;
    }

    @Override
    public Collection<IntelligentService> getByRole(String role) {
        return intelligentServiceRepository.getByRole(role);
    }

    @RabbitListener(queues = "${recommendationService.queue}")
    public void recievedMessageFromRecommendationService(IntelligentService intelligentService) {
        intelligentServiceRepository.save(intelligentService);
        System.out.println("Recieved Message From Recommendation-Service:" + intelligentService.toString());

    }

    //For service provider producer
    @RabbitListener(queues = "${ideaHubService.queue}")
    public void recievedMessageFromIdeaHubService(IntelligentService intelligentService) {
        intelligentServiceRepository.save(intelligentService);
        System.out.println("Recieved Message From Idea-Hub-Service:" + intelligentService.toString());

    }
}
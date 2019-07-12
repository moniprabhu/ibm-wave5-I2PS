package com.stackroute.service;

import com.stackroute.domain.Idea;
import com.stackroute.domain.ServiceProvider;
import com.stackroute.exceptions.EntityNotFoundException;
import com.stackroute.exceptions.IdeaAlreadyExistsException;
import com.stackroute.exceptions.NullIdeaException;
import com.stackroute.repository.IdeaHubRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IdeaHubServiceImpl implements IdeaHubService {
    IdeaHubRepository ideaHubRepository;
    Idea ideaObject;
    //
//    @Override
//    public Idea updateIdea(Idea idea, String ideaId) {
//        return ideaRepository.findOne(ideaId);
//    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${javainuse.idea.exchange}")
    String ideaExchange;

    @Value("${javainuse.idea.routingkey}")
    String ideaRoutingkey;


    @Autowired
    public IdeaHubServiceImpl(IdeaHubRepository ideaRepository)
    {
        this.ideaHubRepository = ideaRepository;
    }

    //CODE FOR ADDING AN IDEA
    @Override
    public Idea addIdea(Idea idea) {
        Idea savedIdea;

            savedIdea = ideaHubRepository.save(idea);
            return savedIdea;

    }

    //CODE FOR DELETING AN IDEA
    @Override
    public void deleteIdea(String ideaId) throws EntityNotFoundException{
        if(ideaHubRepository.existsById(ideaId)) {
            ideaHubRepository.deleteById(ideaId);
        }
        throw new EntityNotFoundException("Idea Not found");
    }


    //CODE FOR UPDATING AN IDEA
    @Override
    public Idea updateIdea(String title, List<ServiceProvider> serviceproviders) throws EntityNotFoundException {
        Idea ideaNew=ideaHubRepository.findByTitle(title);
          if (ideaNew==null) {
//                ideaHubRepository.save(ideaNew);
                throw new EntityNotFoundException("Entry not found");
            }
        ideaNew.setServiceProviders(serviceproviders);

           return ideaHubRepository.save(ideaNew);
//        return ideaNew;
    }

    @Override
    public Optional<Idea> findIdeaById(Idea idea) throws EntityNotFoundException {
        if (ideaHubRepository.existsById(idea.getIdeaId())) {
            return ideaHubRepository.findById(idea.getIdeaId());
        } else throw new EntityNotFoundException("Entry not found");
    }

    //CODE FOR DISPLAYING IDEAs
    @Override
    public List<Idea> displayIdea(Idea idea) throws NullIdeaException {
        if (Objects.isNull(idea))
        { throw new NullIdeaException("No ideas found");}
            return ideaHubRepository.findAll(Sort.by(Sort.Direction.ASC, "timestamp"));
    }

    @Override
    public List<Idea> getByEmailId(String emailId) {
        return ideaHubRepository.findByEmailId(emailId);
    }



    public void send(Idea idea) {
        rabbitTemplate.convertAndSend(ideaExchange, ideaRoutingkey, idea);
        System.out.println("Send msg = " + idea);
    }

}

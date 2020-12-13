package ua.kpi.iasa.IASA_Organiser.service;

import org.springframework.stereotype.Service;
import ua.kpi.iasa.IASA_Organiser.model.Event;
import ua.kpi.iasa.IASA_Organiser.model.Link;
import ua.kpi.iasa.IASA_Organiser.repository.EventRepository;
import ua.kpi.iasa.IASA_Organiser.repository.LinkRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    private final EventRepository eventRepository;

    public LinkService(LinkRepository linkRepository, EventRepository eventRepository) {
        this.linkRepository = linkRepository;
        this.eventRepository = eventRepository;
    }

    public void addLinkToEvent(Link link, UUID eventId) {
        Optional<Event> optEvent = eventRepository.findById(eventId);
        if (optEvent.isEmpty()) {
            return;
        }
        Event event = optEvent.get();
        link.getEvents().add(event);
        linkRepository.save(link);
        event.getLinks().add(link);
        eventRepository.save(event);
    }

    public void removeLinkFromEvent(Link link, UUID eventId) {
        linkRepository.delete(link);
    }
}

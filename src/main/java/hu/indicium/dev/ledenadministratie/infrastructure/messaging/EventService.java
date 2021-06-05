package hu.indicium.dev.ledenadministratie.infrastructure.messaging;

public interface EventService {
    void emitEvent(Event event);
}

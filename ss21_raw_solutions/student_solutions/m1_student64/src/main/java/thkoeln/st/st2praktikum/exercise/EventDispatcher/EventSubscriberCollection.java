package thkoeln.st.st2praktikum.exercise.EventDispatcher;

import thkoeln.st.st2praktikum.exercise.ICollection;

import java.util.HashMap;
import java.util.List;

public class EventSubscriberCollection implements ICollection<IEventSubscriber> {
    private final HashMap<String, List<IEventSubscriber>> subscribers = new HashMap<>();

    public void add(IEventSubscriber eventSubscriber)
    {
        List<IEventSubscriber> eventsSubscribedToEvent = this.subscribers.get(eventSubscriber.getSubscribedEventName());
        eventsSubscribedToEvent.add(eventSubscriber);
    }
}

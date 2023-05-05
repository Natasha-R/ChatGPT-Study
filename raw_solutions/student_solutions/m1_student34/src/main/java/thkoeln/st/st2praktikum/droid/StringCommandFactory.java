package thkoeln.st.st2praktikum.droid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.map.ConnectionRepository;
import thkoeln.st.st2praktikum.map.SpaceShipDeckRepository;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StringCommandFactory {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final ConnectionRepository connectionRepository;
    private final MaintenanceDroidRepository maintenanceDroidRepository;

    public Command createCommand(String input, UUID maintenanceDroidId) {
        var stringTokenizer = new StringTokenizer(input, "[],");
        var commandType = stringTokenizer.nextToken();

        if (Direction.allAbbreviations().anyMatch(commandType::equals)) {
            var distance = Integer.parseInt(stringTokenizer.nextToken());
            return Command.createMapCommand(Direction.of(commandType),
                    distance);
        }
        if (commandType.equals("en")) {
            var spaceShipDeckId = UUID.fromString(stringTokenizer.nextToken());
            var spaceShipDeck =
                    this.spaceShipDeckRepository.findById(spaceShipDeckId)
                            .orElseThrow(IllegalArgumentException::new);
            return Command.createEnterCommand(spaceShipDeck);
        }
        if (commandType.equals("tr")) {
            var targetSpaceShipId =
                    UUID.fromString(stringTokenizer.nextToken());
            var maintenanceDroid =
                    this.maintenanceDroidRepository.findById(maintenanceDroidId)
                            .orElseThrow(NoSuchElementException::new);
            var connection = StreamUtils.createStreamFromIterator(
                    this.connectionRepository
                            .findAllBySource(maintenanceDroid.getPosition())
                            .iterator()
            ).filter(it -> it.getTarget().getMap().getId()
                    .equals(targetSpaceShipId))
                    .findAny().orElseThrow(NoSuchElementException::new);

            return Command.createConnectionCommand(connection);
        }
        throw new IllegalArgumentException();
    }
}

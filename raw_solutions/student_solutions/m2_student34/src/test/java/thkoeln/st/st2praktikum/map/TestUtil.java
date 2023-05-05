package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import thkoeln.st.st2praktikum.linearAlgebra.Straight;

import java.util.List;

public class TestUtil {

    // makes sure, that it is verified with expectedSite.equal
    // (actualSite) and not vice versa
    public static void assertSites(List<? extends Obstacle> actualSites,
                             List<? extends Straight> expectedSites) {
        Assertions.assertThat(actualSites)
                .allMatch(it -> {
                    for (var expectedSite : expectedSites) {
                        if (expectedSite.equals(it)) {
                            return true;
                        }
                    }
                    return false;
                });
    }
}

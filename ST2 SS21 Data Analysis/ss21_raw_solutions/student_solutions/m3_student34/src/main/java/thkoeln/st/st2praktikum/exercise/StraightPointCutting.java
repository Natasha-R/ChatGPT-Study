package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StraightPointCutting implements Cutting {

    private final Straight straight;
    private final Point point;

    @Override
    public Optional<Vector> calculateCut() {
        var lsMatrix = new double[2][2];

        lsMatrix[0][0] = this.straight.getDirectionVector().getX();
        lsMatrix[0][1] = this.point.getCoordinates().subtract(this.straight
                .getOffsetVector()).getX();
        lsMatrix[1][0] = this.straight.getDirectionVector().getY();
        lsMatrix[1][1] = this.point.getCoordinates().subtract(this.straight
                .getOffsetVector()).getY();

        var result = LinearSystem.getInstance().solve(lsMatrix);

        if (result.getSecond() != 1) {
            return Optional.empty();
        }
        return straight.at(result.getFirst()[0]);
    }

    @Override
    public Cuttable getLeftCuttable() {
        return straight;
    }

    @Override
    public Cuttable getRightCuttable() {
        return point;
    }
}

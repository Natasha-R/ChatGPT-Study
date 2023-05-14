package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RectanglePointCutting implements Cutting {

    private final Rectangle rectangle;
    private final Point point;

    @Override
    public Optional<Vector> calculateCut() {
        return checkDistanceToTheRight() && checkDistanceToTheBottom()
                ? Optional.of(this.point.getCoordinates())
                : Optional.empty();
    }

    private boolean checkDistanceToTheRight() {
        var pointCoordinates = point.getCoordinates();
        var upperLeftToPoint =
                pointCoordinates.subtract(this.rectangle.getUpperLeftCorner());
        var upperLeftToUpperRight =
                this.rectangle.getUpperRightCorner().subtract(this.rectangle
                        .getUpperLeftCorner());

        var rightDistance = upperLeftToPoint
                .scalarProduct(upperLeftToUpperRight);
        var leftBound = 0;
        var rightBound =
                upperLeftToUpperRight.scalarProduct(upperLeftToUpperRight);

        return leftBound <= rightDistance && rightDistance <= rightBound;
    }

    private boolean checkDistanceToTheBottom() {
        var pointCoordinates = point.getCoordinates();
        var upperLeftToPoint = pointCoordinates.subtract(this.rectangle
                .getUpperLeftCorner());
        var upperLeftToLowerLeft =
                this.rectangle.getLowerLeftCorner().subtract(this.rectangle
                        .getUpperLeftCorner());

        var lowerDistance = upperLeftToPoint
                .scalarProduct(upperLeftToLowerLeft);
        var upperBound = 0;
        var lowerBound = upperLeftToLowerLeft
                .scalarProduct(upperLeftToLowerLeft);

        return upperBound <= lowerDistance && lowerDistance <= lowerBound;
    }

    @Override
    public Cuttable getLeftCuttable() {
        return this.rectangle;
    }

    @Override
    public Cuttable getRightCuttable() {
        return this.point;
    }
}

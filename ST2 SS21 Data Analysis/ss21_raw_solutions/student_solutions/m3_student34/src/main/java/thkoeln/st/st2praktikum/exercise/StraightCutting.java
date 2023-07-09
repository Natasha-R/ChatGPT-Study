package thkoeln.st.st2praktikum.exercise;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.Optional;

@Data
class StraightCutting implements Cutting {

    protected final Straight leftCuttable;
    protected final Straight rightCuttable;

    @Override
    public Optional<Vector> calculateCut() {
        double[][] lsMatrix = new double[2][3];
        lsMatrix[0][0] = this.leftCuttable.getDirectionVector().getX();
        lsMatrix[0][1] =
                this.rightCuttable.getDirectionVector().inverse().getX();
        lsMatrix[0][2] =
                this.rightCuttable.getOffsetVector()
                        .subtract(this.leftCuttable.getOffsetVector()).getX();
        lsMatrix[1][0] = this.leftCuttable.getDirectionVector().getY();
        lsMatrix[1][1] =
                this.rightCuttable.getDirectionVector().inverse().getY();
        lsMatrix[1][2] =
                this.rightCuttable.getOffsetVector()
                        .subtract(this.leftCuttable.getOffsetVector()).getY();

        Pair<double[], Integer> solution = LinearSystem.getInstance()
                .solve(lsMatrix);

        if (!this.isValidSolution(solution)) {
            return Optional.empty();
        }
        double l1 = solution.getFirst()[0];
        return Optional.of(this.leftCuttable.at(l1).orElseThrow());
    }

    protected boolean isValidSolution(Pair<double[], Integer> solution) {
        var l1 = solution.getFirst()[0];
        var l2 = solution.getFirst()[1];
        return solution.getSecond() == 1
                && this.leftCuttable.at(l1).isPresent()
                && this.rightCuttable.at(l2).isPresent();
    }
}

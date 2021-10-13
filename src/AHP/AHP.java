package AHP;

import Math.Matrix;

import java.util.ArrayList;

public class AHP {
    private ArrayList<Criterion> criteriaList;
    private ArrayList<Alternative> alternativesList;

    private ArrayList<Matrix> alternativeComparisonMatrices;
    private Matrix criterionComparisonMatrix;

    private ArrayList<double[]> weightsList;

    public AHP(Matrix criterionComparisonMatrix, ArrayList<Matrix> alternativeComparisonMatrices) {
        criteriaList = new ArrayList<>();
        alternativesList = new ArrayList<>();
        weightsList = new ArrayList<>();
        this.criterionComparisonMatrix = criterionComparisonMatrix;
        this.alternativeComparisonMatrices = alternativeComparisonMatrices;
    }

    public void addCriteria(Criterion criterion) {
        criteriaList.add(criterion);
    }

    public void addAlternative(Alternative alternative) {
        alternativesList.add(alternative);
    }

    public void createMatrices() {
        for (int i = 0; i < criteriaList.size(); i++) {
            alternativeComparisonMatrices.add(new Matrix(alternativesList.size(), alternativesList.size()));
        }
        criterionComparisonMatrix = new Matrix(criteriaList.size(), criteriaList.size());
    }

    public void calculateWeights() {
        for (int i = 0; i < alternativeComparisonMatrices.size(); i++) {
            double[] vector = alternativeComparisonMatrices.get(i).getEigenvector();

            weightsList.add(vector);
        }
    }

    public double[] getResult() {
        double[] results = new double[alternativesList.size()];

        double[] criteriaWeights = criterionComparisonMatrix.getEigenvector();

        for (int alternativeIndex = 0; alternativeIndex < alternativeComparisonMatrices.size(); alternativeIndex++) {
            for (int criterionIndex = 0; criterionIndex < weightsList.size(); criterionIndex++) {
                results[alternativeIndex] += weightsList.get(criterionIndex)[alternativeIndex] * criteriaWeights[criterionIndex];
            }
        }
        return results;
    }

}

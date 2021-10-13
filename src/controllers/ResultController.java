package controllers;

import AHP.AHP;
import AHP.Alternative;
import AHP.Criterion;
import AHP.DataHolder;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

public class ResultController implements Initializable {
    public GridPane gridPane;

    AHP ahp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createTable();
        ahp = new AHP(CriteriaController.criteriaMatrix, AlternativesController.alternativesByCriteria);
        for (int i = 0; i < DataHolder.alternativesList.size(); i++) {
            ahp.addAlternative(new Alternative(DataHolder.alternativesList.get(i)));
        }
        for (int i = 0; i < DataHolder.criteriaList.size(); i++) {
            ahp.addCriteria(new Criterion(DataHolder.criteriaList.get(i)));
        }
        ahp.calculateWeights();
        double[] result = ahp.getResult();

        LinkedHashMap<String, Double> map = generateAndSortResults(DataHolder.alternativesList, result);

        Arrays.sort(result);
        reverse(result);

        Label nameLabel = new Label();
        Label valueLabel = new Label();
        for (int i = 0; i < DataHolder.alternativesCount; i++) {
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                if (entry.getValue().equals(result[i])) {
                    nameLabel = new Label(entry.getKey());
                    valueLabel = new Label(Double.toString(result[i]));
                    if (i == 0) {
                        nameLabel.setTextFill(Color.web("#ff0000", 0.8));
                        valueLabel.setTextFill(Color.web("#ff0000", 0.8));
                    }
                }
            }
            gridPane.add(nameLabel, 0, i + 1);
            gridPane.add(valueLabel, 1, i + 1);
        }
    }

    private void reverse(double[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            double temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    private LinkedHashMap<String, Double> generateAndSortResults(ArrayList<String> names, double[] values) {
        LinkedHashMap<String, Double> map = new LinkedHashMap<>();
        for (int i = 0; i < names.size(); i++) {
            map.put(names.get(i), values[i]);
        }
        return map;
    }

    private void createTable() {
        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / DataHolder.alternativesCount);
            colConst.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < DataHolder.criteriaCount + 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / DataHolder.alternativesCount);
            rowConst.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConst);
        }
        gridPane.add(new Label("Альтернатива"), 0, 0);
        gridPane.add(new Label("Приоритет"), 1, 0);
    }

}

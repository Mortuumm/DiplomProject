package controllers;

import AHP.DataHolder;
import Math.Matrix;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AlternativesController implements Initializable {
public GridPane gridPane;
public static ArrayList<Matrix> alternativesByCriteria;
    public Label title;
    int currentIndex;
public  static Stage alternativeControllerStage;
@Override
public void initialize(URL location, ResourceBundle resources) {
    alternativesByCriteria = new ArrayList<Matrix>();
    initializeTableForAlternatives();
}

private void initializeTableForAlternatives() {
    title.setText("Сравните Альтернативы по критерию - " + DataHolder.criteriaList.get(currentIndex));
    gridPane.getColumnConstraints().clear();
    gridPane.getRowConstraints().clear();
//        double width = gridPane.getWidth(), height = gridPane.getHeight(), X = gridPane.getLayoutX(), Y = gridPane.getLayoutY();
//        gridPane = new GridPane();
//        gridPane.setPrefSize(width, height);
//        gridPane.setLayoutX(X);
//        gridPane.setLayoutY(Y);
//        gridPane.setGridLinesVisible(true);
    for (int i = 0; i < DataHolder.alternativesCount + 2; i++) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(100.0 / DataHolder.alternativesCount + 2);
        colConst.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().add(colConst);
    }
    for (int i = 0; i < DataHolder.alternativesCount + 1; i++) {
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / DataHolder.alternativesCount + 1);
        rowConst.setValignment(VPos.CENTER);
        gridPane.getRowConstraints().add(rowConst);
    }
    int index = 1;
    gridPane.add(new Label("Название"),1,0);
    for (int i = 1; i < DataHolder.alternativesCount + 1; i++) {
        gridPane.add(new Label("А" + index), 0, i);
        gridPane.add(new Label( DataHolder.alternativesList.get(i - 1)), 1, i);
        gridPane.add(new Label("А" + index++), i + 1, 0);
    }
    for (int i = 0; i < DataHolder.alternativesCount; i++) {
        for (int j = 0; j < DataHolder.alternativesCount; j++) {
            gridPane.add(new TextField(), i + 2, j + 1);
        }
    }
    gridPane.setGridLinesVisible(true);
}

    private void getValuesFromGridIntoAlternativesList() {
        ObservableList<Node> childrens = gridPane.getChildren();
        double[][] array = new double[DataHolder.alternativesCount][DataHolder.alternativesCount];
        for (int j = 0; j < DataHolder.alternativesCount; j++) {
            for (int i = 0; i < DataHolder.alternativesCount; i++) {
                for (Node node : childrens) {
                    Integer row = GridPane.getRowIndex(node);
                    row = GridPane.getRowIndex(node) == null ? new Integer(0) : row;
                    if (row == j + 1 && (GridPane.getColumnIndex(node) == i + 2)) {
                        TextField text = (TextField) node;
                        array[j][i] = Double.parseDouble(text.getText());
                        gridPane.getChildren().remove(node);
                        break;
                    }
                }
            }
        }

        Matrix matrix = new Matrix(array);
    alternativesByCriteria.add(matrix);
}

public void next(ActionEvent actionEvent) {
    getValuesFromGridIntoAlternativesList();
    currentIndex++;
    if (currentIndex == DataHolder.criteriaCount) {
        System.out.println("chill man");
        CriteriaController.criteriaControllerStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/resultForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            //alternativeControllerStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        initializeTableForAlternatives();
    }
}
}

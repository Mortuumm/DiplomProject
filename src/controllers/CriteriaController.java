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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CriteriaController implements Initializable {
    public GridPane gridPane;
    public Label title;
    public static Matrix criteriaMatrix;

    public static Stage criteriaControllerStage;

    public void next(ActionEvent actionEvent) {
        getValuesFromGridIntoCriteriaMatrix();
        HomeController.homeControllerStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/alternativesForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            criteriaControllerStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DataHolder.alternativesCount =  DataHolder.alternativesList.size();
        DataHolder.criteriaCount =  DataHolder.criteriaList.size();
        initializeTable();
    }

    private void initializeTable() {
        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < DataHolder.criteriaCount + 2; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / DataHolder.criteriaCount + 2);
            colConst.setHalignment(HPos.CENTER);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < DataHolder.criteriaCount + 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / DataHolder.criteriaCount + 1);
            rowConst.setValignment(VPos.CENTER);
            gridPane.getRowConstraints().add(rowConst);
        }
        int index = 1;
        gridPane.add(new Label("Название"), 1, 0);
        for (int i = 1; i < DataHolder.criteriaCount + 1; i++) {
            gridPane.add(new Label("Кр" + index), 0, i);
            gridPane.add(new Label( DataHolder.criteriaList.get(i - 1)), 1, i);
            gridPane.add(new Label("Кр" + index++), i + 1, 0);
        }
        for (int i = 0; i < DataHolder.criteriaCount; i++) {
            for (int j = 0; j < DataHolder.criteriaCount; j++) {
                gridPane.add(new TextField(), i + 2, j + 1);
            }
        }
    }

    private void getValuesFromGridIntoCriteriaMatrix() {
        ObservableList<Node> childrens = gridPane.getChildren();
        double[][] array = new double[DataHolder.criteriaCount][DataHolder.criteriaCount];
        for (int j = 0; j < DataHolder.criteriaCount; j++) {
            for (int i = 0; i < DataHolder.criteriaCount; i++) {
                for (Node node : childrens) {
                    Integer row = GridPane.getRowIndex(node);
                    row = GridPane.getRowIndex(node) == null ? new Integer(0) : row;
                    if (row == j + 1 && (GridPane.getColumnIndex(node) == i + 2)) {
                        TextField text = (TextField) node;
                        array[j][i] = Double.parseDouble(text.getText());
                        break;
                    }
                }
            }
        }
        criteriaMatrix = new Matrix(array);
        gridPane.getChildren().removeAll(childrens);
    }
    public void showReference(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Справка");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setContentText("На данном этапе вы сравниваете важность критериев относительно друг друга по ЛИЧНЫМ предпочтениям. Как только значения будут введены, нажмите ДАЛЕЕ и перейдите к следующему этапу.");
        alert.showAndWait();
    }

}

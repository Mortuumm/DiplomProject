package controllers;

import AHP.DataHolder;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
import views.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static Stage homeControllerStage;
    public GridPane alternativesGridPane;
    public GridPane criteriaGridPane;
    private int alt;
    private int crit;

    private void generateTable() {
        alt = 3;
        crit = 3;
        int index = 1;
        alternativesGridPane.setGridLinesVisible(true);
        alternativesGridPane.setAlignment(Pos.CENTER);
        criteriaGridPane.setGridLinesVisible(true);
        criteriaGridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 2);
            colConst.setHalignment(HPos.CENTER);
            alternativesGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < alt + 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / alt);
            rowConst.setValignment(VPos.CENTER);
            alternativesGridPane.getRowConstraints().add(rowConst);
        }

        alternativesGridPane.add(new Label("№"), 0, 0);
        alternativesGridPane.add(new Label("Альтернативы"), 1, 0);

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < alt + 1; j++) {
                if (i == 0) {
                    alternativesGridPane.add(new Label("A" + index++), i, j);
                } else {
                    alternativesGridPane.add(new TextField(), i, j);
                }
            }
        }
        index = 1;
        for (int i = 0; i < 2; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 2);
            colConst.setHalignment(HPos.CENTER);
            criteriaGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < crit + 1; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / crit);
            rowConst.setValignment(VPos.CENTER);
            criteriaGridPane.getRowConstraints().add(rowConst);
        }

        criteriaGridPane.add(new Label("№"), 0, 0);
        criteriaGridPane.add(new Label("Критерии"), 1, 0);

        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < crit + 1; j++) {
                if (i == 0) {
                    criteriaGridPane.add(new Label("Кр" + index++), i, j);
                } else {
                    criteriaGridPane.add(new TextField(), i, j);
                }
            }
        }
    }

    public void next(ActionEvent actionEvent) {
        ObservableList<Node> childrens = alternativesGridPane.getChildren();
        for (int i = 1; i < 2; i++) {
            for (int j = 1; j < alt + 1; j++) {
                for (Node node : childrens) {
                    Integer row = GridPane.getRowIndex(node);
                    row = GridPane.getRowIndex(node) == null ? new Integer(0) : row;
                    if (row == j && (GridPane.getColumnIndex(node) == i)) {
                        TextField text = (TextField) node;
                        DataHolder.alternativesList.add(text.getText());
                        break;
                    }
                }
            }
        }
        childrens = criteriaGridPane.getChildren();
        for (int i = 1; i < 2; i++) {
            for (int j = 1; j < crit + 1; j++) {
                for (Node node : childrens) {
                    Integer row = GridPane.getRowIndex(node);
                    row = GridPane.getRowIndex(node) == null ? new Integer(0) : row;
                    if (row == j && GridPane.getColumnIndex(node) == i) {
                        TextField text = (TextField) node;
                        DataHolder.criteriaList.add(text.getText());
                        break;
                    }
                }
            }
        }
        Main.stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/criterionForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            homeControllerStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAlternative(ActionEvent actionEvent) {
        alt++;
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / alt);
        rowConst.setValignment(VPos.CENTER);
        alternativesGridPane.getRowConstraints().add(rowConst);
        for (int i = 0; i < alternativesGridPane.getRowConstraints().size(); i++)
            alternativesGridPane.getRowConstraints().get(i).setPercentHeight(100.0 / alt);
        alternativesGridPane.add(new Label("A" + alt), 0, alternativesGridPane.getRowConstraints().size() - 1);
        alternativesGridPane.add(new TextField(), 1, alternativesGridPane.getRowConstraints().size() - 1);
    }

    public void removeAlternative(ActionEvent actionEvent) {
        alt--;
        removeNodeByRowColumnIndex(alternativesGridPane.getRowConstraints().size() - 1, 0, alternativesGridPane);
        removeNodeByRowColumnIndex(alternativesGridPane.getRowConstraints().size() - 1, 1, alternativesGridPane);
        alternativesGridPane.getRowConstraints().remove(alternativesGridPane.getRowConstraints().size() - 1);
        for (int i = 0; i < alternativesGridPane.getRowConstraints().size(); i++)
            alternativesGridPane.getRowConstraints().get(i).setPercentHeight(100.0 / alt);
    }

    public void addCriterion(ActionEvent actionEvent) {
        crit++;
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(100.0 / crit);
        rowConst.setValignment(VPos.CENTER);
        criteriaGridPane.getRowConstraints().add(rowConst);
        for (int i = 0; i < criteriaGridPane.getRowConstraints().size(); i++)
            criteriaGridPane.getRowConstraints().get(i).setPercentHeight(100.0 / crit);
        criteriaGridPane.add(new Label("Кр." + crit), 0, criteriaGridPane.getRowConstraints().size() - 1);
        criteriaGridPane.add(new TextField(), 1, criteriaGridPane.getRowConstraints().size() - 1);
    }

    public void removeCriterion(ActionEvent actionEvent) {
        crit--;
        removeNodeByRowColumnIndex(criteriaGridPane.getRowConstraints().size() - 1, 0, criteriaGridPane);
        removeNodeByRowColumnIndex(criteriaGridPane.getRowConstraints().size() - 1, 1, criteriaGridPane);
        criteriaGridPane.getRowConstraints().remove(criteriaGridPane.getRowConstraints().size() - 1);
        for (int i = 0; i < criteriaGridPane.getRowConstraints().size(); i++)
            criteriaGridPane.getRowConstraints().get(i).setPercentHeight(100.0 / crit);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateTable();
    }

    public void removeNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof Label && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                Label imageView = (Label) node; // use what you want to remove
                gridPane.getChildren().remove(imageView);
                break;
            }
            if (node instanceof TextField && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                TextField imageView = (TextField) node;
                gridPane.getChildren().remove(imageView);
                break;
            }
        }
    }

    public void showReference(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Справка");
        alert.setHeaderText(null);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label)node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setContentText("Стандандартное количество критериев и альтернатив 3x3. Вы можете добавить или удалить новые по желанию. Введите названия и перейдите к следющему шагу");
        alert.showAndWait();
    }
}

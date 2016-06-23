package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.java.com.ipsos.andreas.asignari.animation.FadeInLeftTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInUpTransition;
import main.java.com.ipsos.andreas.asignari.database.ProjectsDB;
import main.java.com.ipsos.andreas.asignari.model.Project;
import main.java.com.ipsos.andreas.asignari.utils.Loading;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;


/**
 * Created by Andreas on 6/14/16.
 */
public class TeamProjectsFragment {

    @FXML private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private GridPane PaneTabel;
    @FXML private HBox PaneTop;
    @FXML private AnchorPane PaneMain;
    @FXML private TableView<Project> TableProjects;
    @FXML private TableColumn<Project, String> ColumnStatus;
    @FXML private TableColumn<Project, String> ColumnAssignDate;
    @FXML private TableColumn<Project, String> ColumnDP_RO;
    @FXML private TableColumn<Project, String> ColumnDP_NA;
    @FXML private TableColumn<Project, String> ColumnJobNumber;
    @FXML private TableColumn<Project, String> ColumnJobTitle;
    @FXML private TableColumn<Project, String> ColumnJobType;
    @FXML private TableColumn<Project, String> ColumnClient;
    @FXML private TableColumn<Project, String> ColumnCS;
    @FXML private TableColumn<Project, String> ColumnFieldStart;
    @FXML private TableColumn<Project, String> ColumnFieldEnd;
    @FXML private TableColumn<Project, String> ColumnCheckIpsos;
    @FXML private TableColumn<Project, String> ColumnCheckCS;
    @FXML private TableColumn<Project, String> ColumnFinalCheckIpsos;
    @FXML private TableColumn<Project, String> ColumnFinalCheckCS;
    @FXML private TableColumn<Project, String> ColumnAnalyticsToCS;
    @FXML private TableColumn<Project, String> ColumnOtherDeliverables;
    @FXML private TableColumn<Project, Double> ColumnVendorBudget;
    @FXML private TableColumn<Project, Double> ColumnOverseeBudget;
    @FXML private TableColumn<Project, Double> ColumnReserveBudget;
    @FXML private TableColumn<Project, Double> ColumnIquoteBudget;
    private ObservableList<Project> listProjects;
    @FXML private Text TextEmptyTable;
    @FXML private HBox PaneSearch;
    @FXML private TextField FieldSearch;
    @FXML private ComboBox ComboSearch;
    @FXML private ComboBox ComboSearchShow;
    @FXML private Button ButtonSearch2;
    @FXML private Button ButtonSearch;

    public void initialize() throws SQLException {
        PaneTabel.getChildren().remove(PaneSearch);

        ComboSearch.getItems().addAll("All", "Assign date", "DP RO", "DP NA", "Job number", "Job title",
                "Job type", "Client", "CS", "Field start", "Field end", "Check Ipsos", "Check CS", "Final check Ipsos",
                "Final check CS", "Analytics to CS", "Others deliverables");
        ComboSearch.setValue("All");

        ComboSearchShow.getItems().addAll("All", "Pending", "Active", "Closed");
        ComboSearchShow.setValue("All");

        listProjects = FXCollections.observableArrayList(ProjectsDB.getAllProjects());

        ButtonSearch.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonSearch.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonSearch2.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonSearch2.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ImageLoading.setVisible(true);
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        Task task= Loading.load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ImageLoading.setVisible(false);
                PaneTop.setVisible(true);
                if (listProjects.isEmpty()) {
                    TextEmptyTable.setVisible(true);
                } else {
                    initializeTable();
                }
                new FadeInLeftTransition(PaneTop).play();
                new FadeInUpTransition(PaneTabel).play();
            }
        });

    }

    public void initializeTable() {
        PaneTabel.setVisible(true);
        TableProjects.setItems(listProjects);
        ColumnStatus.setCellValueFactory(new PropertyValueFactory<Project, String>("status"));
        ColumnAssignDate.setCellValueFactory(new PropertyValueFactory<Project, String>("assignDate"));
        ColumnDP_RO.setCellValueFactory(new PropertyValueFactory<Project, String>("DP_RO"));
        ColumnDP_NA.setCellValueFactory(new PropertyValueFactory<Project, String>("DP_NA"));
        ColumnJobNumber.setCellValueFactory(new PropertyValueFactory<Project, String>("jobNumber"));
        ColumnJobTitle.setCellValueFactory(new PropertyValueFactory<Project, String>("jobTitle"));
        ColumnJobType.setCellValueFactory(new PropertyValueFactory<Project, String>("jobType"));
        ColumnClient.setCellValueFactory(new PropertyValueFactory<Project, String>("client"));
        ColumnCS.setCellValueFactory(new PropertyValueFactory<Project, String>("CS"));
        ColumnFieldStart.setCellValueFactory(new PropertyValueFactory<Project, String>("fieldStart"));
        ColumnFieldEnd.setCellValueFactory(new PropertyValueFactory<Project, String>("fieldEnd"));
        ColumnCheckIpsos.setCellValueFactory(new PropertyValueFactory<Project, String>("checkIpsos"));
        ColumnCheckCS.setCellValueFactory(new PropertyValueFactory<Project, String>("checkCS"));
        ColumnFinalCheckIpsos.setCellValueFactory(new PropertyValueFactory<Project, String>("finalCheckIpsos"));
        ColumnFinalCheckCS.setCellValueFactory(new PropertyValueFactory<Project, String>("finalCheckCS"));
        ColumnAnalyticsToCS.setCellValueFactory(new PropertyValueFactory<Project, String>("analyticsToCS"));
        ColumnOtherDeliverables.setCellValueFactory(new PropertyValueFactory<Project, String>("otherDeliverables"));
        ColumnVendorBudget.setCellValueFactory(new PropertyValueFactory<Project, Double>("vendorBudget"));
        ColumnOverseeBudget.setCellValueFactory(new PropertyValueFactory<Project, Double>("overseeBudget"));
        ColumnReserveBudget.setCellValueFactory(new PropertyValueFactory<Project, Double>("reserveBudget"));
        ColumnIquoteBudget.setCellValueFactory(new PropertyValueFactory<Project, Double>("iquoteBudget"));
    }

    @FXML
    public void showSearch() {
        if (PaneTabel.getChildren().contains(PaneSearch)) {
            PaneTabel.getChildren().remove(PaneSearch);
        } else {
            PaneTabel.add(PaneSearch,0,0);
        }
    }

    @FXML
    public void searchInTables() {
        if (!ComboSearchShow.getValue().toString().equals("All")) {
            try {
                listProjects = FXCollections.observableArrayList(ProjectsDB.getAllProjects().stream().filter(p -> p.getStatus().equals(ComboSearchShow.getValue().toString())).collect(Collectors.toCollection(() -> new ArrayList<Project>())));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        FilteredList<Project> filteredData = new FilteredList<>(listProjects, p -> true);
        filteredData.setPredicate(project -> {
            // If filter text is empty, display all projects.
            if (FieldSearch.getText() == null || FieldSearch.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = FieldSearch.getText().toLowerCase();

            switch (ComboSearch.getValue().toString()) {
                case "All":{
                    if (project.getAssignDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getDP_RO().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getDP_NA().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getJobNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getJobNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getJobTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getJobType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getClient().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getFieldStart().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getFieldEnd().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getCheckIpsos().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getCheckCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getFinalCheckIpsos().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getFinalCheckCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getAnalyticsToCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else
                    if (project.getOtherDeliverables().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                }break;
                case "Assign date":
                    if (project.getAssignDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "DP RO":
                    if (project.getDP_RO().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "DP NA":
                    if (project.getDP_NA().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Job number":
                    if (project.getJobNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Job title":
                    if (project.getJobTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Job type":
                    if (project.getJobType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Client":
                    if (project.getClient().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "CS":
                    if (project.getCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Field start":
                    if (project.getFieldStart().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Field end":
                    if (project.getFieldEnd().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Check Ipsos":
                    if (project.getCheckIpsos().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Check CS":
                    if (project.getCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Final check Ipsos":
                    if (project.getFinalCheckIpsos().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Final check CS":
                    if (project.getFinalCheckCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Analytics to CS":
                    if (project.getAnalyticsToCS().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
                case "Others deliverables":
                    if (project.getOtherDeliverables().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    }
                    break;
            }
            return false;
        });
        SortedList<Project> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TableProjects.comparatorProperty());
        if (sortedData.isEmpty()) {
            Text empty_text=new Text();
            empty_text.setFill(Color.web("#8b0000"));
            empty_text.setTextAlignment(TextAlignment.CENTER);
            empty_text.setFont(Font.font ("Times New Roman", FontWeight.BOLD,16));
            empty_text.setText("Nu s-a gasit niciun rezultat! Incercati din nou cu alt sir de caractere.");
            TableProjects.setPlaceholder(empty_text);
            TableProjects.setPrefHeight(100);
        }
        TableProjects.setItems(sortedData);
    }
}

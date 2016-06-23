package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;
import main.java.com.ipsos.andreas.asignari.animation.FadeInLeftTransition;
import main.java.com.ipsos.andreas.asignari.animation.FadeInUpTransition;
import main.java.com.ipsos.andreas.asignari.common.ScreenController;
import main.java.com.ipsos.andreas.asignari.common.Shared;
import main.java.com.ipsos.andreas.asignari.database.ProjectsDB;
import main.java.com.ipsos.andreas.asignari.model.Project;
import main.java.com.ipsos.andreas.asignari.ui.AlertMessage;
import main.java.com.ipsos.andreas.asignari.utils.Loading;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static main.java.com.ipsos.andreas.asignari.common.StageManager.getStage;

/**
 * Created by Andreas on 6/14/16.
 */
public class MyProjectsFragment {

    @FXML private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private GridPane PaneAdd;
    @FXML private GridPane PaneTabel;
    @FXML private HBox PaneTop;
    @FXML private ComboBox ComboStatus;
    @FXML private DatePicker DateAssignDate;
    @FXML private TextField FieldDP_RO;
    @FXML private TextField FieldDP_NA;
    @FXML private TextField FieldJobTitle;
    @FXML private TextField FieldJobNumber;
    @FXML private ComboBox ComboJobType;
    @FXML private TextField FieldClient;
    @FXML private TextField FieldCS;
    @FXML private DatePicker DateFieldStart;
    @FXML private DatePicker DateFieldEnd;
    @FXML private DatePicker DateCheckIpsos;
    @FXML private DatePicker DateCheckCS;
    @FXML private DatePicker DateFinalCheckIpsos;
    @FXML private DatePicker DateFinalCheckCS;
    @FXML private TextField FieldVendorBudget;
    @FXML private TextField FieldOverseeBudget;
    @FXML private TextField FieldReserveBudget;
    @FXML private TextArea FieldAnalyticsToCS;
    @FXML private TextArea FieldOthersDeliverables;
    @FXML private Button ButtonBack;
    @FXML private Button ButtonSearch;
    @FXML private Button ButtonNew;
    @FXML private Button ButtonSave;
    @FXML private AnchorPane PaneMain;
    @FXML private TableView<Project> TableProjects;
    @FXML private TableColumn<Project, HBox> ColumnAction;
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
    @FXML private Text TextEmptyTable;
    @FXML private HBox PaneSearch;
    @FXML private TextField FieldSearch;
    @FXML private ComboBox ComboSearch;
    @FXML private ComboBox ComboSearchShow;
    @FXML private Button ButtonSearch2;
    private ObservableList<Project> listProjects;
    private String old_jobNumber;

    public void initialize() throws SQLException {
        FieldSearch.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void  handle(KeyEvent keyEvent)
            {
                if (keyEvent.getCode()== KeyCode.ENTER)
                    searchInTables();
            }
        });

        PaneTabel.getChildren().remove(PaneSearch);

        ComboSearch.getItems().addAll("All", "Assign date", "DP RO", "DP NA", "Job number", "Job title",
                "Job type", "Client", "CS", "Field start", "Field end", "Check Ipsos", "Check CS", "Final check Ipsos",
                "Final check CS", "Analytics to CS", "Others deliverables");
        ComboSearch.setValue("All");

        ComboSearchShow.getItems().addAll("All", "Pending", "Active", "Closed");
        ComboSearchShow.setValue("All");

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        DateAssignDate.setConverter(converter);
        DateFieldEnd.setConverter(converter);
        DateFieldStart.setConverter(converter);
        DateCheckCS.setConverter(converter);
        DateFinalCheckCS.setConverter(converter);
        DateCheckIpsos.setConverter(converter);
        DateFinalCheckIpsos.setConverter(converter);

        ButtonBack.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonBack.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonNew.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonNew.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonSearch.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonSearch.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ButtonSearch2.setOnMouseEntered(e->getStage().getScene().setCursor(Cursor.HAND));
        ButtonSearch2.setOnMouseExited(e->getStage().getScene().setCursor(Cursor.DEFAULT));

        ComboStatus.getItems().addAll("Pending", "Active", "Closed");
        ComboStatus.setValue("Pending");

        ComboJobType.getItems().addAll("Adhoc", "Brand Tracking", "Concept Test", "Deisgnor", "MediaCT", "Observer",
                "Pack Test", "Product Test", "REID EAST", "Shopper Quant","Other");
        ComboJobType.setValue("None");

        PaneMain.getChildren().remove(PaneAdd);
        PaneMain.getChildren().remove(PaneTabel);

        listProjects = FXCollections.observableArrayList(ProjectsDB.getMyProjects());

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
                if (Shared.project!=null) {
                    editProject();
                } else {
                    PaneTop.setVisible(true);
                    PaneMain.getChildren().add(PaneTabel);
                    if (listProjects.isEmpty()) {
                        TextEmptyTable.setVisible(true);
                    } else {
                        initializeTable();
                    }
                    new FadeInLeftTransition(PaneTop).play();
                    new FadeInUpTransition(PaneTabel).play();
                }
            }
        });

    }

    public void initializeTable() {
        PaneTabel.setVisible(true);
        TableProjects.setItems(listProjects);
        ColumnAction.setCellValueFactory(new PropertyValueFactory<Project, HBox>("action"));
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
    public void saveAdd() {
        try {
            ProjectsDB.addProject(new Project(ComboStatus.getValue().toString(),
                    DateAssignDate.getValue()==null?"":converDate(DateAssignDate.getValue()),
                    FieldDP_RO.getText(),FieldDP_NA.getText(),FieldJobNumber.getText(),FieldJobTitle.getText(),
                    ComboJobType.getValue().toString(),FieldClient.getText(),FieldCS.getText(),
                    DateFieldStart.getValue()==null?"":converDate(DateFieldStart.getValue()),
                    DateFieldEnd.getValue()==null?"":converDate(DateFieldEnd.getValue()),
                    DateCheckIpsos.getValue()==null?"":converDate(DateCheckIpsos.getValue()),
                    DateCheckCS.getValue()==null?"":converDate(DateCheckCS.getValue()),
                    DateFinalCheckIpsos.getValue()==null?"":converDate(DateFinalCheckIpsos.getValue()),
                    DateFinalCheckCS.getValue()==null?"":converDate(DateFinalCheckCS.getValue())
                    ,FieldAnalyticsToCS.getText(), FieldOthersDeliverables.getText(),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldVendorBudget.getText()),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldOverseeBudget.getText()),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldReserveBudget.getText())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new AlertMessage("Success","Your project is added into database!", Alert.AlertType.INFORMATION);
        back();
    }

    public void saveEdit() {
        try {
            ProjectsDB.editProject(new Project(ComboStatus.getValue().toString(),
                    DateAssignDate.getValue()==null?"":converDate(DateAssignDate.getValue()),
                    FieldDP_RO.getText(),FieldDP_NA.getText(),FieldJobNumber.getText(),FieldJobTitle.getText(),
                    ComboJobType.getValue().toString(),FieldClient.getText(),FieldCS.getText(),
                    DateFieldStart.getValue()==null?"":converDate(DateFieldStart.getValue()),
                    DateFieldEnd.getValue()==null?"":converDate(DateFieldEnd.getValue()),
                    DateCheckIpsos.getValue()==null?"":converDate(DateCheckIpsos.getValue()),
                    DateCheckCS.getValue()==null?"":converDate(DateCheckCS.getValue()),
                    DateFinalCheckIpsos.getValue()==null?"":converDate(DateFinalCheckIpsos.getValue()),
                    DateFinalCheckCS.getValue()==null?"":converDate(DateFinalCheckCS.getValue())
                    ,FieldAnalyticsToCS.getText(), FieldOthersDeliverables.getText(),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldVendorBudget.getText()),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldOverseeBudget.getText()),
                    FieldVendorBudget.getText().isEmpty()?0:Double.parseDouble(FieldReserveBudget.getText())),old_jobNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new AlertMessage("Success","Your project is added into database!", Alert.AlertType.INFORMATION);
        back();
    }

    public void editProject() {
        Project project=Shared.project;
        old_jobNumber=project.getJobNumber();
        Shared.project=null;
        PaneTop.setVisible(false);
        new FadeInUpTransition(PaneAdd).play();
        PaneMain.getChildren().remove(PaneTabel);
        PaneMain.getChildren().add(PaneAdd);
        ComboStatus.setValue(project.getStatus());
        FieldDP_RO.setText(project.getDP_RO());
        FieldDP_NA.setText(project.getDP_NA());
        FieldJobNumber.setText(project.getJobNumber());
        FieldJobTitle.setText(project.getJobTitle());
        ComboJobType.setValue(project.getJobType());
        FieldClient.setText(project.getClient());
        FieldCS.setText(project.getCS());
        DateAssignDate.setValue(converDateBack(project.getAssignDate()));
        DateFieldStart.setValue(converDateBack(project.getFieldStart()));
        DateFieldEnd.setValue(converDateBack(project.getFieldEnd()));
        DateCheckIpsos.setValue(converDateBack(project.getCheckIpsos()));
        DateCheckCS.setValue(converDateBack(project.getCheckCS()));
        DateFinalCheckIpsos.setValue(converDateBack(project.getFinalCheckIpsos()));
        DateFinalCheckCS.setValue(converDateBack(project.getFinalCheckCS()));
        FieldVendorBudget.setText(String.valueOf(project.getVendorBudget()));
        FieldOverseeBudget.setText(String.valueOf(project.getOverseeBudget()));
        FieldReserveBudget.setText(String.valueOf(project.getReserveBudget()));
        FieldAnalyticsToCS.setText(project.getAnalyticsToCS());
        FieldOthersDeliverables.setText(project.getOtherDeliverables());

        ButtonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveEdit();
            }
        });
    }
    @FXML
    public void back() {
        try {
            ScreenController.setScreen(ScreenController.Screen.MY_PROJECTS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void newProject() {
        TextEmptyTable.setVisible(false);
        PaneTop.setVisible(false);
        new FadeInUpTransition(PaneAdd).play();
        PaneMain.getChildren().remove(PaneTabel);
        PaneMain.getChildren().add(PaneAdd);
    }
    @FXML
    public void showSearch() {
        if (PaneTabel.getChildren().contains(PaneSearch)) {
            PaneTabel.getChildren().remove(PaneSearch);
        } else {
            PaneTabel.add(PaneSearch,0,0);
        }
    }

    public String converDate(LocalDate dateToConvert) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return dateFormatter.format(dateToConvert);
    }

    public LocalDate converDateBack(String dateToConvert) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
        return LocalDate.parse(dateToConvert,dateFormatter);
    }

    @FXML
    public void searchInTables() {
        if (!ComboSearchShow.getValue().toString().equals("All")) {
            try {
                listProjects = FXCollections.observableArrayList(ProjectsDB.getMyProjects().stream().filter(p -> p.getStatus().equals(ComboSearchShow.getValue().toString())).collect(Collectors.toCollection(() -> new ArrayList<Project>())));
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


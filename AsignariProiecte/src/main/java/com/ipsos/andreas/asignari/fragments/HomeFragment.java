package main.java.com.ipsos.andreas.asignari.fragments;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.java.com.ipsos.andreas.asignari.animation.FadeInUpTransition;
import main.java.com.ipsos.andreas.asignari.database.EmployeesDB;
import main.java.com.ipsos.andreas.asignari.database.ProjectsDB;
import main.java.com.ipsos.andreas.asignari.model.Employee;
import main.java.com.ipsos.andreas.asignari.utils.Loading;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Andreas on 6/14/16.
 */
public class HomeFragment {

    @FXML private ImageView ImageLoading;
    @FXML private ProgressBar ProgressLoading;
    @FXML private BarChart<String, Number> ChartHome;
    @FXML private AnchorPane PaneMain;
    @FXML private VBox PaneHome;
    private Task task;
    private XYChart.Series series;
    private List<Employee> listEmployees;

    public void initialize() throws SQLException {
        PaneHome.getChildren().remove(ChartHome);

        try {
            listEmployees= EmployeesDB.getAllEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageLoading.setVisible(true);
        ProgressLoading.setProgress(0);
        ProgressLoading.progressProperty().unbind();
        task= load();
        ProgressLoading.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ImageLoading.setVisible(false);
                PaneHome.getChildren().add(ChartHome);
                new FadeInUpTransition(ChartHome).play();
            }
        });

    }

    private String getDate(int dayOfWeek) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMMM-yyyy");
        return dateFormatter.format(c.getTime());
    }

    public Task load() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                int i=0;
                for (Employee employee:listEmployees) {
                    series = new XYChart.Series();
                    series.setName(employee.getFirstName() + " " + employee.getLastName());

                    series.getData().add(new XYChart.Data("Monday", ProjectsDB.getNumberOfProjects(EmployeesDB.getEmployeeID(employee.getUsername()),getDate(2))));
                    series.getData().add(new XYChart.Data("Tuesday", ProjectsDB.getNumberOfProjects(EmployeesDB.getEmployeeID(employee.getUsername()),getDate(3))));
                    series.getData().add(new XYChart.Data("Wednesday", ProjectsDB.getNumberOfProjects(EmployeesDB.getEmployeeID(employee.getUsername()),getDate(4))));
                    series.getData().add(new XYChart.Data("Thursday", ProjectsDB.getNumberOfProjects(EmployeesDB.getEmployeeID(employee.getUsername()),getDate(5))));
                    series.getData().add(new XYChart.Data("Friday", ProjectsDB.getNumberOfProjects(EmployeesDB.getEmployeeID(employee.getUsername()),getDate(6))));

                    ChartHome.getData().add(series);
                    updateProgress(i + 1, listEmployees.size());
                    i++;
                }
                return true;
            }
        };
    }
}

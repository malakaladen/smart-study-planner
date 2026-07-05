package studyplanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Main extends Application {

    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    private MainView view;

    @Override
    public void start(Stage stage) {
        // Create view and pass reference to Main (this)
        view = new MainView(this);

        // Connect the Subject.java list to the table
        view.getTableView().setItems(subjectList);

        // Show the app
        view.show(stage);
    }

    // ========== ALL LOGIC METHODS HERE ==========

    public void handleAdd(String name, LocalDate date, int difficulty, String hoursText) {
        try {
            int hours = Integer.parseInt(hoursText);

            if (name.isEmpty()) {
                view.showAlert("Error", "Please enter Subject.java name");
                return;
            }
            if (hours <= 0) {
                view.showAlert("Error", "Hours must be positive");
                return;
            }
            if (date.isBefore(LocalDate.now())) {
                view.showAlert("Error", "Exam date cannot be in the past");
                return;
            }

            Subject subject = new Subject(name, date, difficulty, hours);
            subjectList.add(subject);

            view.clearForm();
            view.setStatusMessage("Added: " + name + " | Total subjects: " + subjectList.size());

        } catch (NumberFormatException ex) {
            view.showAlert("Error", "Please enter valid hours (number)");
        }
    }

    public void handleCalculate() {
        if (subjectList.isEmpty()) {
            view.showAlert("Info", "Add some subjects first!");
            return;
        }

        for (Subject subject : subjectList) {
            long daysLeft = subject.getDaysUntilExam();
            int difficulty = subject.getDifficulty();
            int hours = subject.getHoursNeeded();

            double priority = (difficulty * 2.5) + (hours * 1.2) + (25.0 / (daysLeft + 0.5));
            subject.setPriorityScore(priority);
        }

        view.refreshTable();
        view.setStatusMessage("Priority calculated! Click 'SORT' to see the order");
        view.showAlert("Success", "Priority scores updated!");
    }

    public void handleDelete(Subject selected) {
        if (selected != null) {
            subjectList.remove(selected);
            view.setStatusMessage("Subject deleted | Total subjects: " + subjectList.size());
        } else {
            view.showAlert("Error", "Please select a Subject.java to delete");
        }
    }

    public void handleSort() {
        if (subjectList.isEmpty()) {
            view.showAlert("Info", "No subjects to sort");
            return;
        }

        subjectList.sort((s1, s2) -> Double.compare(s2.getPriorityScore(), s1.getPriorityScore()));
        view.refreshTable();
        view.setStatusMessage("Sorted by priority! Highest priority subjects shown first");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

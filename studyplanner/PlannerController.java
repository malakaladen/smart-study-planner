package studyplanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class PlannerController {
    private ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    private MainView view;
    public PlannerController(MainView view){
        this.view = view;
        view.getTableView().setItems(subjectList);
    }

    public void handleAdd(String name, LocalDate date, int difficulty, String hoursText){
        try{
            int hours = Integer.parseInt(hoursText);

            if (name.isEmpty()){
                showAlert("Error", "Please enter subject name");
                return;
            }
            if(hours <=0){
                showAlert ("Error","Hours must be positive");
                return;
            }
            if(date.isBefore(LocalDate.now())){
                showAlert("Error", "Exam date cannot be in the past");
                return;
            }
            Subject subject= new Subject(name, date, difficulty, hours);
            subjectList.add(subject);
            view.clearForm();
            view.setStatusMessage("Added: "+name+" | Total subjects: "+ subjectList.size());
        } catch (NumberFormatException ex){
            showAlert("Error", "Please enter valid hours (number)");
        }
    }
    public void handleCalculate(){
        if( subjectList.isEmpty()){
            showAlert("Info", "Add some subjects first!");
            return;
        }
        for(Subject subject : subjectList){
            long daysLeft = subject.getDaysUntilExam();
            int difficulty = subject.getDifficulty();
            int hours = subject.getHoursNeeded();
            double priority = (difficulty *2.5) + (hours *1.2) + (25.0 / (daysLeft+0.5));
            subject.setPriorityScore(priority);
        }
        view.refreshTable();
        view.setStatusMessage("Priority calculated! Click 'SORT' to see the order");
        showAlert("Success", "Priority scores updated!");
    }
    public void handleDelete(Subject selected){
        if(selected != null){
            subjectList.remove(selected);
            view.setStatusMessage("Subject deleted | Total subjects: " + subjectList.size());
        } else {
            showAlert ("Error", "Please select a subject to delete");
        }
    }
    public void handleSort(){
        if(subjectList.isEmpty()){
            showAlert("Info", "No subjects to sort");
            return;
        }
        subjectList.sort((s1,s2) -> Double.compare(s2.getPriorityScore(),s1.getPriorityScore()));
        view.refreshTable();
        view.setStatusMessage("Sorted by priority! Highest priority subjects shown first");
    }
    private void showAlert(String title, String message){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

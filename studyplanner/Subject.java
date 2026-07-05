package studyplanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Subject {
    private String name;
    private LocalDate examDate;
    private int difficulty;
    private int hoursNeeded;
    private double priorityScore;

    //constructor
    public Subject(String name, LocalDate examDate, int difficulty, int hoursNeeded){
        this.name = name;
        this.examDate = examDate;
        this.difficulty = difficulty;
        this.hoursNeeded = hoursNeeded;
        this.priorityScore = 0.0;
    }
    //Getters and setters
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public  LocalDate getExamDate(){
        return examDate;
    }
    public void setExamDate(LocalDate examDate){
        this.examDate = examDate;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public void setDifficulty( int difficulty){
        this.difficulty= difficulty;
    }
    public int getHoursNeeded(){
        return hoursNeeded;
    }
    public void setHoursNeeded( int hoursNeeded){
        this.hoursNeeded = hoursNeeded;
    }
    public double getPriorityScore(){
        return priorityScore;
    }
    public void setPriorityScore( double priorityScore){
        this.priorityScore = priorityScore;
    }
    //calculate days until exam
    public long getDaysUntilExam(){
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(today,examDate);
    }
    @Override
    public String toString(){
        return name + "(Priority: "+String.format("%.2f", priorityScore)+")";
    }
}

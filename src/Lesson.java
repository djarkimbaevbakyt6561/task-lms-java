public class Lesson {
    String lessonName;
    String taskDescription;

    @Override
    public String toString() {
        return "Урок " + lessonName +
                ", oписание задачи: " + taskDescription;
    }
}

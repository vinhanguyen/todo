package todo.model;

public class Task {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public static void main(String[] args) {
        Task task = new Task();
        task.setDescription("watch tv");
        
        System.out.println("task: " + task.getDescription());
    }
}

package todo.model;

public class Task {

    private int id;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

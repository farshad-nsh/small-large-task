public class taskEventMachine {

    private static class Task {
        public String name;
        public Task(String name) {
            this.name = name;
        }
    }

    private static class TaskSmall extends Event {
        public Task task;
        public String message;
        public TaskSmall(Task task, String message) {
            this.task = task;
            this.message = message;
        }
    }

    private static class TaskLarge extends Event {
        public Task task;
        public TaskLarge(Task task) {
            this.task = task;
        }
    }
    public static void main(String[] args) {
        EventDispatcher dispatcher = new EventDispatcher();
        dispatcher.registerChannel(TaskSmall.class, new Handler() {
            @Override
        public void dispatch(Event evt) {
            TaskSmall small = (TaskSmall)evt;
            String taskSmall = String.format("%s:%s", small.task.name,small.message+ " a small task is received" );
            System.out.println(taskSmall);
         }
        });
        dispatcher.registerChannel(TaskLarge.class, new Handler() {
            @Override
        public void dispatch(Event evt) {
            TaskLarge large = (TaskLarge)evt;
            System.out.println(large.task.name + ":a large task is received");
         }
        });
        Task foo = new Task("from server1");
        Task bar = new Task("from server2");
        dispatcher.dispatch(new TaskLarge(foo));
        dispatcher.dispatch(new TaskLarge(bar));
        dispatcher.dispatch(new TaskSmall(foo, "simple calculation!"));
        dispatcher.dispatch(new TaskSmall(bar, "quick translation!"));
        dispatcher.dispatch(new TaskSmall(foo, "quick speech recognition!"));
        dispatcher.dispatch(new TaskLarge(foo));
    }
}

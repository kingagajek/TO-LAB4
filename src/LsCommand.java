public class LsCommand implements Command {
    private Directory directory;

    public LsCommand(Directory directory) {
        this.directory = directory;
    }

    @Override
    public void execute() {
        System.out.println("Zawartość katalogu " + directory.getName() + ":");
        for (NodeComponent child : directory.getChildrenNodes()) {
            System.out.println(child.getName());
        }
    }
}

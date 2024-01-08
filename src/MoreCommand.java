public class MoreCommand implements Command {
    private Directory currentDirectory;
    private File filename;

    public MoreCommand(Directory currentDirectory, File filename) {
        this.currentDirectory = currentDirectory;
        this.filename = filename;
    }

    @Override
    public void execute() {
        System.out.println(filename.getContent());
    }
}

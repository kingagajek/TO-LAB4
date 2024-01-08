public class MvCommand implements Command {
    private Directory currentDirectory;
    private String sourcePath;
    private String destinationPath;

    public MvCommand(Directory currentDirectory, String sourcePath, String destinationPath) {
        this.currentDirectory = currentDirectory;
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    @Override
    public void execute() {
        NodeComponent source = currentDirectory.findNodeByPath(sourcePath);
        NodeComponent destination = currentDirectory.findNodeByPath(destinationPath);

        if (source == null) {
            System.out.println("Nie znaleziono źródła: " + sourcePath);
            return;
        }
        if (destination == null || !(destination instanceof Directory)) {
            System.out.println("Nieprawidłowy katalog docelowy: " + destinationPath);
            return;
        }
        move(source, (Directory) destination);
    }

    private void move(NodeComponent source, Directory destination) {
        if (source == destination) {
            System.out.println("Źródło i cel są identyczne.");
            return;
        }

        if (source.getParentNode() != null && source.getParentNode() instanceof Directory) {
            ((Directory) source.getParentNode()).removeChildNode(source);
        }

        destination.addChildNode(source);
        source.setParentNode(destination);
    }
}

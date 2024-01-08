public class CpCommand implements Command {
    private Directory currentDirectory;
    private String sourcePath;
    private String destinationPath;

    public CpCommand(Directory currentDirectory, String sourcePath, String destinationPath) {
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

        copy(source, (Directory) destination);
    }

    private void copy(NodeComponent source, Directory destination) {
        if (source instanceof Directory) {
            Directory newDir = new Directory(source.getName(), destination);
            destination.addChildNode(newDir);
            for (NodeComponent child : ((Directory) source).getChildrenNodes()) {
                copy(child, newDir);
            }
        } else if (source instanceof File) {
            File newFile = new File(source.getName(), destination, ((File) source).getContent());
            destination.addChildNode(newFile);
        }
    }
}

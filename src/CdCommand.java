public class CdCommand implements Command {
    private Directory rootDirectory;
    private Directory currentDirectory;
    private String path;
    private Directory newCurrentDirectory;
    public CdCommand(Directory rootDirectory, Directory currentDirectory, String path) {
        this.rootDirectory = rootDirectory;
        this.currentDirectory = currentDirectory;
        this.path = path;
    }

    @Override
    public void execute() {
        Directory targetDirectory = path.startsWith("/") ? rootDirectory : currentDirectory;
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.equals("..")) {
                if (targetDirectory.getParentNode() != null) {
                    targetDirectory = (Directory) targetDirectory.getParentNode();
                }
            } else if (!part.isEmpty() && !part.equals(".")) {
                Directory nextDirectory = targetDirectory.getSubdirectory(part);
                if (nextDirectory != null) {
                    targetDirectory = nextDirectory;
                } else {
                    System.out.println("Nie znaleziono katalogu: " + part);
                    return;
                }
            }
        }
        setCurrentDirectory(targetDirectory);
    }

    private void setCurrentDirectory(Directory directory) {
        if (directory != currentDirectory) {
            newCurrentDirectory = directory;
        }
    }

    public Directory getCurrentDirectory() {
        return newCurrentDirectory;
    }

    public boolean hasChangedDirectory() {
        return newCurrentDirectory != null;
    }
}

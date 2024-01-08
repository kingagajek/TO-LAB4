import java.util.regex.Pattern;

public class TouchCommand implements Command {
    private Directory currentDirectory;
    private String filename;

    private static final Pattern VALID_FILENAME_PATTERN = Pattern.compile("^[a-zA-Z0-9.]+$");
    public TouchCommand(Directory currentDirectory, String filename) {
        this.currentDirectory = currentDirectory;
        this.filename = filename;
    }

    @Override
    public void execute() {
        if (!VALID_FILENAME_PATTERN.matcher(filename).matches()) {
            System.out.println("Błąd: Nazwa pliku może zawierać tylko litery, cyfry i kropkę.");
            return;
        }
        File file = findFileInDirectory(currentDirectory, filename);

        if (file == null) {
            file = new File(filename, currentDirectory, "");
            currentDirectory.addChildNode(file);
            System.out.println("Utworzono nowy plik: " + filename);
        } else {
            file.updateLastModified();
            System.out.println("Plik " + filename + " już istnieje, aktualizowano timestamp.");
        }
    }

    private File findFileInDirectory(Directory directory, String filename) {
        for (NodeComponent node : directory.getChildrenNodes()) {
            if (node instanceof File && node.getName().equals(filename)) {
                return (File) node;
            }
        }
        return null;
    }
}

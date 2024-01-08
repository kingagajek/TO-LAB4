import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Directory root = new Directory("root", null);

        Directory dev = new Directory("dev", root);
        Directory usr = new Directory("usr", root);
        Directory docs = new Directory("docs", root);

        Directory admin = new Directory("admin", usr);

        File fileTxt = new File("file.txt", docs, "Zawartość pliku file.txt");

        root.addChildNode(dev);
        root.addChildNode(usr);
        root.addChildNode(docs);

        usr.addChildNode(admin);

        docs.addChildNode(fileTxt);

        Directory currentDirectory = root;

        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("Witaj w symulatorze systemu plików!");
        System.out.println("Wpisz 'exit' aby zakończyć.");

        // Pętla główna programu
        while (true) {
            if (currentDirectory != null) {
                System.out.print(currentDirectory.getFullPath() + "> ");
            } else {
                System.out.print("(brak katalogu)> ");
            }

            input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                break;
            }

            String[] commands = input.split(" ");
            if (commands.length > 0) {
                Command command = null;
                switch (commands[0].toLowerCase()) {
                    case "cd":
                        if (commands.length < 2) {
                            System.out.println("Brak argumentu dla komendy cd.");
                        } else {
                            command = new CdCommand(root, currentDirectory, commands[1]);
                        }
                        break;
                    case "ls":
                        command = new LsCommand(currentDirectory);
                        break;
                    case "touch":
                        if (commands.length < 2) {
                            System.out.println("Brak argumentu dla komendy touch.");
                        } else {
                            command = new TouchCommand(currentDirectory, commands[1]);
                        }
                        break;
                    case "cp":
                        if (commands.length < 3) {
                            System.out.println("Brak argumentów dla komendy cp.");
                        } else {
                            command = new CpCommand(currentDirectory, commands[1], commands[2]);
                        }
                        break;
                    case "mv":
                        if (commands.length < 3) {
                            System.out.println("Brak argumentów dla komendy mv.");
                        } else {
                            command = new MvCommand(currentDirectory, commands[1], commands[2]);
                        }
                        break;
                    case "more":
                        if (commands.length < 2) {
                            System.out.println("Brak argumentu dla komendy more.");
                        } else {
                            String filename = commands[1];
                            File file = currentDirectory.findFileByName(filename);
                            if (file != null) {
                                command = new MoreCommand(currentDirectory, file);
                            } else {
                                System.out.println("Plik " + filename + " nie został znaleziony.");
                            }
                        }
                        break;

                }

                if (command != null) {
                    try {
                        command.execute();
                        if (command instanceof CdCommand) {
                            Directory newDir = ((CdCommand) command).getCurrentDirectory();
                            if (newDir != null) {
                                currentDirectory = newDir;
                            } else {
                                System.out.println("Nieprawidłowa ścieżka.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Wystąpił błąd: " + e.getMessage());
                    }
                }
            }
        }

        scanner.close();
        System.out.println("Zamykanie symulatora systemu plików.");
    }
}

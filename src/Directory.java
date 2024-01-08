import java.util.ArrayList;
import java.util.List;

public class Directory extends NodeComponent {
    private List<NodeComponent> childrenNodeComponents;

    public Directory(String name, NodeComponent parent) {
        super(name, parent);
        this.childrenNodeComponents = new ArrayList<>();
    }

    public void addChildNode(NodeComponent nodeComponent) {
        childrenNodeComponents.add(nodeComponent);
    }

    public void removeChildNode(NodeComponent nodeComponent) {
        childrenNodeComponents.remove(nodeComponent);
    }

    public List<NodeComponent> getChildrenNodes() {
        return childrenNodeComponents;
    }

    public Directory getSubdirectory(String name) {
        for (NodeComponent child : childrenNodeComponents) {
            if (child instanceof Directory && child.getName().equals(name)) {
                return (Directory) child;
            }
        }
        return null;
    }

    public NodeComponent findNodeByPath(String path) {
        String[] parts = path.split("/");
        NodeComponent current = this;

        for (String part : parts) {
            if (part.equals("..")) {
                if (current.getParentNode() != null) {
                    current = current.getParentNode();
                } else {
                    throw new IllegalArgumentException("Próba wyjścia poza root.");
                }
            } else if (!part.isEmpty() && !part.equals(".")) {
                if (current instanceof Directory) {
                    boolean found = false;
                    for (NodeComponent child : ((Directory)current).getChildrenNodes()) {
                        if (child.getName().equals(part)) {
                            current = child;
                            found = true;
                            break;
                        }
                    }
                    if (!found) return null;
                } else {
                    throw new IllegalArgumentException("Ścieżka prowadzi przez plik, który nie jest katalogiem.");
                }
            }
        }
        return current;
    }

    public File findFileByName(String name) {
        for (NodeComponent child : this.getChildrenNodes()) {
            if (child instanceof File && child.getName().equals(name)) {
                return (File) child;
            }
        }
        return null;
    }

    public String getFullPath() {
        if (this.getParentNode() == null || !(this.getParentNode() instanceof Directory)) {
            return this.getName();
        } else {
            return ((Directory) this.getParentNode()).getFullPath() + "/" + this.getName();
        }
    }

}
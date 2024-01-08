public abstract class NodeComponent {
    private NodeComponent parentNodeComponent;
    private String name;

    public NodeComponent(String name, NodeComponent parentNodeComponent) {
        this.name = name;
        this.parentNodeComponent = parentNodeComponent;
    }

    @Override
    public NodeComponent clone() {
        try {
            NodeComponent cloned = (NodeComponent) super.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Klonowanie nie jest wspierane", e);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeComponent getParentNode() {
        return parentNodeComponent;
    }

    public void setParentNode(NodeComponent parentNodeComponent) {
        this.parentNodeComponent = parentNodeComponent;
    }
}

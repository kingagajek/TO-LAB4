public class File extends NodeComponent {
    private String content;
    private long lastModified;
    public File(String name, NodeComponent parent, String content) {
        super(name, parent);
        this.content = content;
        this.lastModified = System.currentTimeMillis();
    }

    public void updateLastModified() {
        this.lastModified = System.currentTimeMillis();
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public NodeComponent clone() {
        return new File(this.getName(), this.getParentNode(), new String(this.content));
    }


}

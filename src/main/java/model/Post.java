package model;

import java.util.List;
import java.util.Objects;

public class Post {

    private Long id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;

    public Post(Long id, String content, String created, String updated, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return  Objects.equals(id, post.id) &&
                Objects.equals(content, post.content) &&
                Objects.equals(created, post.created) &&
                Objects.equals(updated, post.updated) &&
                Objects.equals(labels, post.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", lebels=" + labels +
                '}';
    }
}

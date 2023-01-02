package pl.migibud.blog.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.migibud.blog.comment.Comment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"title"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String content;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Set<Comment> comments = new HashSet<>();

    public Post(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Post(Long id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(description, post.description) && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, content);
    }
}

package pl.migibud.blog.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.migibud.blog.post.Post;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    public Comment(String name, String email, String body, Post post) {
        this.name = name;
        this.email = email;
        this.body = body;
        this.post = post;
    }
}

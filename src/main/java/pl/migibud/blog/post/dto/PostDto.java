package pl.migibud.blog.post.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.migibud.blog.comment.dto.CommentDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2,message = "Post title should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Post title should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    public PostDto(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
package t1.homeworks.synthetichumancore.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import t1.homeworks.synthetichumancore.constant.Priority;
import t1.homeworks.synthetichumancore.util.validation.ValidEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Command {
    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Priority must be specified")
    @ValidEnum(enumClass = Priority.class)
    private Priority priority;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 100, message = "Author must not exceed 100 characters")
    private String author;

    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?(Z|[+-]\\d{2}:\\d{2})?$",
            message = "Time must be in ISO 8601 format"
    )
    private String time;
}

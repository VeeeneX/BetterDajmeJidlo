package cz.muni.fi.pv217.lepsiedajmejedlo.payments.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@Document(collection = "user-credits")
@JsonDeserialize(builder = UserCredits.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
public class UserCredits {

    @Id
    @NotNull
    @NotEmpty
    private final String userId;

    @NotNull
    @Min(value = 0, message = "Credit count must be positive integer")
    private final Long creditCount;
}

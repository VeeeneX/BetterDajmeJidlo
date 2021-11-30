package cz.muni.fi.pv217.lepsiedajmejedlo.payments.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode(exclude = "id")
@Document(collection = "payments")
@JsonDeserialize(builder = Payment.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
public class Payment {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String id;

    @NotNull
    @NotEmpty
    private final String userId;

    @NotNull
    @Min(value = 1, message = "Credit amount must be positive non zero integer")
    private final Long creditAmount;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private final Date paymentDate;
}

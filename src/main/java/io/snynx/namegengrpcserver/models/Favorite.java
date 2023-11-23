package io.snynx.namegengrpcserver.models;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "namer.favorite")
public class Favorite {
    @Id long favid;
    long uid;
    String name1;
    String name2;
}

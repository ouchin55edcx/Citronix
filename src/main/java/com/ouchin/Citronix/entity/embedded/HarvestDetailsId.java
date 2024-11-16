package com.ouchin.Citronix.entity.embedded;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HarvestDetailsId {

    private Long harvestId;
    private Long treeId;

}

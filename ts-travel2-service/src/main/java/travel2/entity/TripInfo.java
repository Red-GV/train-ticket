package travel2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
public class TripInfo {
    @Valid
    @NotNull
    private String startingPlace;

    @Valid
    @NotNull
    private String endPlace;

    @Valid
    @NotNull
    private Date departureTime;

    public TripInfo(){
        //Default Constructor
        this.startingPlace = "";
        this.endPlace = "";
        this.departureTime = new Date();
    }

}

package adminbasic.entity;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * @author fdse
 */
@Data
public class Station {

    private String id;

    @Valid
    @NotNull
    private String name;

    private int stayTime;

    public Station(){
        this.name = "";
    }



    public Station(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public Station(String id, String name, int stayTime) {
        this.id = id;
        this.name = name;
        this.stayTime = stayTime;
    }

}

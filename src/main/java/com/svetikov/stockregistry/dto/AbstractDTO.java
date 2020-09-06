package com.svetikov.stockregistry.dto;


import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
public class AbstractDTO implements Serializable {

    @Null(groups = {New.class})
    @NotNull(groups = {Existing.class})
    @JsonView({StandardView.class, DetailView.class})
    Long id;

    public interface New {
    }

    public interface Existing {
    }

    public interface StandardView {
    }

    public interface DetailView {

    }
}

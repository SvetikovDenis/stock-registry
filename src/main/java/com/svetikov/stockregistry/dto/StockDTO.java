package com.svetikov.stockregistry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.sql.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockDTO extends AbstractDTO {

    @NotBlank(groups = {New.class}, message = "Comment can't be null or blank")
    @JsonView({StandardView.class,DetailView.class})
    private String comment;

    @NotBlank(groups = {New.class}, message = "Edrpou can't be null or blank")
    @JsonView({StandardView.class,DetailView.class})
    private String edrpou;

    @NotNull(groups = {New.class}, message = "Quantity can't be null ")
    @JsonView({StandardView.class,DetailView.class})
    private Integer quantity;

    @NotNull(groups = {New.class}, message = "Nominal value can't be null ")
    @JsonView({StandardView.class,DetailView.class})
    private Integer nominalValue;

    @Null(groups = {New.class},message = "Total value should be null")
    @JsonView({StandardView.class,DetailView.class})
    private Integer totalValue;

    @NotNull(groups = {New.class}, message = "Issue date can't be null ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonView({StandardView.class,DetailView.class})
    private Date issueDate;

    @Null(groups = {New.class},message = "Status should be null")
    @JsonView({StandardView.class,DetailView.class})
    private String status;

    public StockDTO() {
    }

    @Builder
    public StockDTO(Long id, String comment, String edrpou, Integer quantity, Integer nominalValue, Integer totalValue, Date issueDate, String status) {
        this.id = id;
        this.comment = comment;
        this.edrpou = edrpou;
        this.quantity = quantity;
        this.nominalValue = nominalValue;
        this.totalValue = totalValue;
        this.issueDate = issueDate;
        this.status = status;
    }
}

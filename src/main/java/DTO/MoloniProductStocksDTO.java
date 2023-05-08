package DTO;

import Constants.ConstantsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoloniProductStocksDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("company_id")
    private Long companyId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product_id")
    private Long productId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("movement_date")
    private String movementDateString;

    @JsonIgnore(value = true)
    private LocalDateTime movementDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document_id")
    private Long documentId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("qty")
    private Integer quantityMoved;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("accumulated")
    private Integer quantityAfterMovement;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private MoloniProductDTO moloniProductDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("document")
    private MoloniDocumentDTO documentDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("offset")
    private Integer skipFirstResults;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("movement_date_under") //yyyy-MM-dd
    private String movementDateBefore;

    public MoloniProductStocksDTO(Long productId) {
        this.companyId = Long.parseLong(ConstantsEnum.MOLONI_COMPANY_ID.getConstantValue().toString());
        this.productId = productId;
    }

    public MoloniProductStocksDTO() {

    }

    public String getMovementDateBefore() {
        return this.movementDateBefore;
    }

    public void setMovementDateBefore(final String movementDateBefore) {
        this.movementDateBefore = movementDateBefore;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Integer getQuantityMoved() {
        return quantityMoved;
    }

    public String getMovementDateString() {
        return movementDateString;
    }

    public void setMovementDateString(String movementDateString) {
        this.movementDateString = movementDateString;
    }

    public LocalDateTime getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(LocalDateTime movementDate) {
        this.movementDate = movementDate;
    }

    public Integer getSkipFirstResults() {
        return this.skipFirstResults;
    }

    public void setSkipFirstResults(final Integer skipFirstResults) {
        this.skipFirstResults = skipFirstResults;
    }

    public void setQuantityMoved(Integer quantityMoved) {
        this.quantityMoved = quantityMoved;
    }

    public Integer getQuantityAfterMovement() {
        return quantityAfterMovement;
    }

    public void setQuantityAfterMovement(Integer quantityAfterMovement) {
        this.quantityAfterMovement = quantityAfterMovement;
    }

    public MoloniProductDTO getMoloniProductDTO() {
        return moloniProductDTO;
    }

    public void setMoloniProductDTO(MoloniProductDTO moloniProductDTO) {
        this.moloniProductDTO = moloniProductDTO;
    }

    public MoloniDocumentDTO getDocumentDTO() {
        return documentDTO;
    }

    public void setDocumentDTO(MoloniDocumentDTO documentDTO) {
        this.documentDTO = documentDTO;
    }
}

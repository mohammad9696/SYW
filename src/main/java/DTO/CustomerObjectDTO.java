package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerObjectDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customer")
    CustomerDTO customerDTO;

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public CustomerObjectDTO() {
    }

    public CustomerObjectDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}

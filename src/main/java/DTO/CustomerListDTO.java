package DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerListDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("customers")
    List<CustomerDTO> customerDTO;

    public List<CustomerDTO> getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(List<CustomerDTO> customerDTO) {
        this.customerDTO = customerDTO;
    }
}

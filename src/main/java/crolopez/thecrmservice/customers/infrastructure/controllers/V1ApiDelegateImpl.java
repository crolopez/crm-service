package crolopez.thecrmservice.customers.infrastructure.controllers;

import crolopez.thecrmservice.api.V1ApiDelegate;
import crolopez.thecrmservice.model.CustomerDTODto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class V1ApiDelegateImpl implements V1ApiDelegate {
    @Override
    public ResponseEntity<List<CustomerDTODto>> getCustomers() {
        return ResponseEntity.ok().body(List.of());
    }

}

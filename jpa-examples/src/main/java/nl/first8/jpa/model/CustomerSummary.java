package nl.first8.jpa.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerSummary {

    String getName();
    AddressSummary getAddress();

    interface AddressSummary {
        String getCity();
    }

}

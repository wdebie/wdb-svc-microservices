package fact.it.artistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistResponse {
    private String artistId;
    private String skuCode;
    private String name;
//    private String repEmail;
//    private BigDecimal bookingPrice;
//    private String repPhone;
}

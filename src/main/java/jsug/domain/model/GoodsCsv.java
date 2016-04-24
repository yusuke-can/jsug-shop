package jsug.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoodsCsv {

  @JsonProperty("goods_id")
  private UUID goodsId;

  @JsonProperty("goods_name")
  private String goodsName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("category_id")
  private Integer categoryId;

  @JsonProperty("category_name")
  private String categoryName;

  @JsonProperty("price")
  private int price;
}

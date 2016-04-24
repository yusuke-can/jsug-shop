/**
 *
 */
package jsug.app.csv;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Data
public class GoodsCsvRequest {

  @NotEmpty
  @NotNull
  private List<Integer> categoryIds;

  private Integer limit;

  private Integer offset;
}

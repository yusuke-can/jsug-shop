package jsug.app.csv;

import java.util.List;
import java.util.Optional;

import jsug.domain.model.GoodsCsv;
import jsug.domain.service.csv.GoodsCsvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jsug")
public class CsvController {


  private static final Integer DEFAULT_CSV_LIMIT = 6_5536;

  @Autowired
  private GoodsCsvService goodsCsvService;

  @RequestMapping(method = RequestMethod.GET, value = {"/goods/v1"},
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public @ResponseBody List<GoodsCsv> getGootsCsv(@ModelAttribute @Validated  GoodsCsvRequest request) {
    final int limit = Optional.ofNullable(request.getLimit()).orElse(DEFAULT_CSV_LIMIT);
    final int offset = Optional.ofNullable(request.getOffset()).orElse(0);

    if (offset >= limit) {
      throw new IllegalArgumentException("csv row offset is greater then csv row limit. reqeuest: "
          + request.toString());
    }
    return goodsCsvService.findByCategoryIds(request.getCategoryIds(), limit, offset);
  }


}

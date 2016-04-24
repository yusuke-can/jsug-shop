package jsug.domain.service.csv;

import java.util.Collections;
import java.util.List;

import jsug.domain.model.GoodsCsv;
import jsug.domain.repository.goods.GoodsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoodsCsvService {
  @Autowired
  GoodsRepository goodsRepository;

  /**
   * 商品のカテゴリーIDのリストから該当する商品情報一覧を返却.</br>
   *
   * <pre>
   * http://localhost:8080/jsug/goods/v1?categoryIds=1,2&size=10&offset=0
   * </pre>
   *
   * @param categoryIds カテゴリーIDのリスト
   * @param limit 返すリストの上限
   * @param offset selectした一覧のオフセット（何個目から返すか、最初のindexは0であることに注意）
   * @return
   */
  @Transactional(readOnly = true)
  public List<GoodsCsv> findByCategoryIds(List<Integer> categoryIds, int limit, int offset) {
    final long totalSize = goodsRepository.countByCategoryIds(categoryIds);
    if (offset >= totalSize) {
      return Collections.emptyList();
    }
    return goodsRepository.findByCategoryIds(categoryIds, limit, offset);
  }
}

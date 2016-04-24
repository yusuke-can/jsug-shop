package jsug.domain.repository.goods;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jsug.domain.model.Category;
import jsug.domain.model.Goods;
import jsug.domain.model.GoodsCsv;
import jsug.domain.repository.SqlFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class GoodsRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    SqlFinder sql;

    RowMapper<Goods> goodsRowMapper = (rs, i) -> Goods.builder()
            .goodsId(UUID.fromString(rs.getString("goods_id")))
            .goodsName(rs.getString("goods_name"))
            .category(Category.builder()
                    .categoryId(rs.getInt("category_id"))
                    .categoryName(rs.getString("category_name"))
                    .build())
            .description(rs.getString("description"))
            .price(rs.getInt("price"))
            .build();

    // CSVは複数のメソッドで共有することは少なそうなのでローカル変数にしてもいいかも
    RowMapper<GoodsCsv> goodsCsvRowMapper = (rs, i) -> GoodsCsv.builder()
        .goodsId(UUID.fromString(rs.getString("goods_id")))
        .goodsName(rs.getString("goods_name"))
        .categoryId(rs.getInt("category_id"))
        .categoryName(rs.getString("category_name"))
        .description(rs.getString("description"))
        .price(rs.getInt("price"))
        .build();

    public Optional<Goods> findOne(UUID goodsId) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("goodsId", goodsId.toString());
        return jdbcTemplate.query(sql.get("sql/goods/findOne.sql"), source, goodsRowMapper)
                .stream()
                .findFirst();
    }

    public Page<Goods> findByCategoryId(int categoryId, Pageable pageable) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("categoryId", categoryId)
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        List<Goods> content = jdbcTemplate.query(sql.get("sql/goods/findByCategoryId.sql"), source, goodsRowMapper);
        long total = countByCategoryId(categoryId);
        return new PageImpl<>(content, pageable, total);
    }

    public List<GoodsCsv> findByCategoryIds(List<Integer> categoryIds, int limit, int offset) {
      SqlParameterSource source = new MapSqlParameterSource()
              .addValue("categoryIds", categoryIds)
              .addValue("limit", limit)
              .addValue("offset", offset);
      return jdbcTemplate.query(sql.get("sql/goods/findByCategoryIds.sql"), source, goodsCsvRowMapper);
  }

    public long countByCategoryId(int categoryId) {
        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("categoryId", categoryId);
        return jdbcTemplate.queryForObject(sql.get("sql/goods/countByCategoryId.sql"), source, Long.class);
    }

    public long countByCategoryIds(List<Integer> categoryIds) {
      SqlParameterSource source = new MapSqlParameterSource()
              .addValue("categoryIds", categoryIds);
      return jdbcTemplate.queryForObject(sql.get("sql/goods/countByCategoryIds.sql"), source, Long.class);
  }
}

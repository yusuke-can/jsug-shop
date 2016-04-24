SELECT COUNT(*)
FROM goods AS g, category AS c
WHERE
  c.category_id IN (:categoryIds)
  AND
  g.category_id = c.category_id

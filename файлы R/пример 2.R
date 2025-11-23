# Преобразование данных для boxplot
library(tidyr)

price_data <- stores_data %>%
  select(store, segment, min_price, max_price) %>%
  tidyr::gather(key = "price_type", value = "price", min_price, max_price)

# Boxplot минимальных и максимальных цен по сегментам
ggplot(price_data, aes(x = segment, y = price, fill = price_type)) +
  geom_boxplot(position = position_dodge(0.8), alpha = 0.7) +
  scale_fill_manual(
    values = c("min_price" = "lightgreen", "max_price" = "coral"),
    labels = c("Минимальная цена", "Максимальная цена"),
    name = "Тип цены"
  ) +
  labs(
    title = "Распределение минимальных и максимальных цен по сегментам",
    x = "Ценовой сегмент",
    y = "Цена (руб)"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(hjust = 0.5),
    legend.position = "bottom"
  )

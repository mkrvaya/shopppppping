library(ggplot2)

stores_data <- data.frame(
  store = c('ORGANIC PEOPLE', 'EcoSense', 'Vittoria Vicci', 'Puro Lino Eco', 
            'A1FA', 'Annemore', 'Chintamani', 'MATERIA'),
  min_price = c(103, 136, 347, 4352, 311, 717, 2000, 505),
  avg_rating = c(2.12, 3.79, 4.61, 4.59, 4.67, 4.79, 4.79, 4.80),
  segment = c('Базовый', 'Базовый', 'Джинсы', 'Базовый', 
              'Джинсы', 'Премиум', 'Премиум', 'Премиум')
)

# Диаграмма рассеяния с цветовым кодированием по сегментам
ggplot(stores_data, aes(x = min_price, y = avg_rating, color = segment)) +
  geom_point(size = 4, alpha = 0.8) +
  geom_text(aes(label = store), vjust = -0.8, size = 3, check_overlap = TRUE) +
  scale_color_manual(
    values = c("Базовый" = "#FF6B6B", "Джинсы" = "#4ECDC4", "Премиум" = "#45B7D1"),
    name = "Сегмент:"
  ) +
  labs(
    title = "Диаграмма рассеяния: Связь между минимальной ценой и рейтингом",
    subtitle = "Цветом обозначены ценовые сегменты магазинов",
    x = "Минимальная цена (руб)",
    y = "Средний рейтинг"
  ) +
  theme_minimal() +
  theme(
    plot.title = element_text(hjust = 0.5),
    plot.subtitle = element_text(hjust = 0.5),
    legend.position = "bottom"
  )

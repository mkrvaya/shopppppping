# Загрузка библиотеки
library(ggplot2)

# Создание данных для boxplot
stores_data <- data.frame(
  segment = c('Базовый', 'Базовый', 'Средний', 'Базовый', 
              'Средний', 'Премиум', 'Премиум', 'Премиум'),
  avg_rating = c(2.12, 3.79, 4.61, 4.59, 4.67, 4.79, 4.79, 4.80)
)

# Построение boxplot
ggplot(stores_data, aes(x = segment, y = avg_rating, fill = segment)) +
  geom_boxplot(alpha = 0.7, show.legend = FALSE) +
  geom_point(size = 3, alpha = 0.6) +  # Добавляем точки для наглядности
  scale_fill_manual(values = c("Базовый" = "lightcoral", 
                               "Средний" = "lightgreen", 
                               "Премиум" = "lightblue")) +
  labs(title = "Распределение рейтингов по ценовым сегментам",
       x = "Ценовой сегмент",
       y = "Средний рейтинг") +
  theme_minimal() +
  theme(plot.title = element_text(hjust = 0.5, size = 14),
        axis.title = element_text(size = 12),
        axis.text = element_text(size = 10))


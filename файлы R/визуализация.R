# Загружаем библиотеки
library(ggplot2)

# Читаем данные
data <- read.csv("products_202511030051.csv")

# 1. График распределения рейтингов
ggplot(data, aes(x = review_rating)) +
  geom_histogram(fill = "lightblue", color = "black", bins = 20) +
  labs(title = "Распределение рейтингов товаров",
       x = "Рейтинг", y = "Количество товаров")

# 2. График цен по брендам
ggplot(data, aes(x = product_brand, y = price)) +
  geom_boxplot(fill = "lightgreen") +
  labs(title = "Цены по брендам",
       x = "Бренд", y = "Цена") +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))

# 3. График связи цены и рейтинга
ggplot(data, aes(x = price, y = review_rating)) +
  geom_point(color = "red", alpha = 0.5) +
  labs(title = "Связь цены и рейтинга",
       x = "Цена", y = "Рейтинг")

# 4. График отзывов по брендам
ggplot(data, aes(x = product_brand, y = feedbacks)) +
  geom_col(fill = "orange") +
  labs(title = "Количество отзывов по брендам",
       x = "Бренд", y = "Отзывы") +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))
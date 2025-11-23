# Просто загружаем библиотеки и данные
library(dplyr)
products_data <- read_csv("products_202511022252.csv")

# Создаем простые ценовые сегменты на основе цены
products_data <- products_data %>%
  mutate(
    price_segment = case_when(
      price > 300000 ~ "премиум",
      price > 100000 ~ "средний", 
      TRUE ~ "бюджет"
    )
  )

# Тест 1: Различия в ценах между сегментами
test1 <- kruskal.test(price ~ price_segment, data = products_data)
print(test1)

# Тест 2: Связь цены и рейтинга  
test2 <- cor.test(products_data$price, products_data$review_rating, method = "spearman")
print(test2)

# Тест 3: Связь отзывов и рейтинга
test3 <- cor.test(products_data$feedbacks, products_data$review_rating, method = "spearman")
print(test3)
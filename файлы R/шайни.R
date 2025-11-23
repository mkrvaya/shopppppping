library(shiny)
library(dplyr)
library(DT)

products_data <- read.csv("products_202511030051.csv", stringsAsFactors = FALSE) %>%
  mutate(
    price_rub = price / 100
  )

analyze_brand <- function(brand_data) {
  brand_name <- unique(brand_data$product_brand)
  
  product_types <- brand_data$product_name
  common_words <- c("джинс", "юбк", "плать", "рубаш", "куртк", "пальто", "свитер", "брюк", "шорт", "пиджак")
  detected_types <- character()
  
  for(word in common_words) {
    if(any(grepl(word, tolower(product_types)))) {
      detected_types <- c(detected_types, word)
    }
  }
  
  # Анализ цен
  min_price <- min(brand_data$price_rub, na.rm = TRUE)
  max_price <- max(brand_data$price_rub, na.rm = TRUE)
  
  # Анализ рейтингов
  avg_rating <- round(mean(brand_data$review_rating, na.rm = TRUE), 2)
  rating_range <- paste0(round(min(brand_data$review_rating, na.rm = TRUE), 1), 
                         "-", round(max(brand_data$review_rating, na.rm = TRUE), 1))
  
  # Анализ отзывов
  zero_feedbacks <- sum(brand_data$feedbacks == 0, na.rm = TRUE)
  total_products <- nrow(brand_data)
  
  # Анализ размеров
  sizes_data <- brand_data$sizes
  unique_sizes <- unique(sizes_data)
  size_features <- character()
  
  if(any(grepl("оверсайз", tolower(unique_sizes)))) size_features <- c(size_features, "оверсайз")
  if(any(grepl("единый", tolower(unique_sizes)))) size_features <- c(size_features, "единый размер")
  if(any(grepl("/", unique_sizes))) size_features <- c(size_features, "сложная размерная сетка (рост/обхват)")
  if(any(grepl("-", unique_sizes))) size_features <- c(size_features, "диапазоны размеров")
  
  list(
    name = brand_name,
    product_types = detected_types,
    min_price = min_price,
    max_price = max_price,
    avg_rating = avg_rating,
    rating_range = rating_range,
    zero_feedbacks = zero_feedbacks,
    total_products = total_products,
    size_features = size_features
  )
}

ui <- fluidPage(
  titlePanel("Анализ товаров Wildberries"),
  
  sidebarLayout(
    sidebarPanel(
      width = 3,
      selectInput("selected_brand", "Выберите магазин:",
                  choices = unique(products_data$product_brand)),
      hr(),
      h4("Сортировка товаров"),
      
      selectInput("sort_primary", "Основная сортировка:",
                  choices = c("Без сортировки" = "none",
                              "Цена" = "price",
                              "Рейтинг" = "rating", 
                              "Отзывы" = "feedbacks",
                              "Наличие" = "quantity")),
      
      selectInput("sort_primary_order", "Порядок:",
                  choices = c("По убыванию" = "desc",
                              "По возрастанию" = "asc")),
      
      selectInput("sort_secondary", "Дополнительная сортировка:",
                  choices = c("Без сортировки" = "none",
                              "Цена" = "price",
                              "Рейтинг" = "rating", 
                              "Отзывы" = "feedbacks",
                              "Наличие" = "quantity")),
      
      selectInput("sort_secondary_order", "Порядок:",
                  choices = c("По убыванию" = "desc",
                              "По возрастанию" = "asc"))
    ),
    
    mainPanel(
      width = 9,
      tabsetPanel(
        tabPanel("Общая статистика",
                 h3("Автоматический анализ магазинов"),
                 uiOutput("brand_analysis")
        ),
        
        tabPanel("Товары магазина",
                 h3("Товары выбранного магазина"),
                 dataTableOutput("products_table")
        )
      )
    )
  )
)

server <- function(input, output, session) {
  output$brand_analysis <- renderUI({
    brands <- unique(products_data$product_brand)
    analysis_list <- list()
    
    for(brand in brands) {
      brand_data <- products_data %>% filter(product_brand == brand)
      analysis <- analyze_brand(brand_data)
      
      analysis_list[[brand]] <- tagList(
        h4(paste(analysis$name, "-", get_brand_category(analysis))),
        p(strong("Товары:"), paste(get_product_types(analysis$product_types), collapse = ", ")),
        p(strong("Цены:"), paste(analysis$min_price, "-", analysis$max_price, "руб.")),
        p(strong("Рейтинги:"), analysis$rating_range, paste0("(средний: ", analysis$avg_rating, ")")),
        p(strong("Отзывы:"), paste0(analysis$zero_feedbacks, " из ", analysis$total_products, " товаров без отзывов")),
        p(strong("Особенности:"), paste(analysis$size_features, collapse = ", ")),
        hr()
      )
    }
    
    tagList(analysis_list)
  })
  
  get_brand_category <- function(analysis) {
    if(analysis$avg_rating >= 4.7 && analysis$max_price > 5000) return("Премиум одежда")
    if(any(grepl("джинс", analysis$product_types))) return("Джинсовая одежда")
    if(any(grepl("прокладк|гигиен", analysis$product_types))) return("Гигиена и спорт")
    if(any(grepl("оверсайз", analysis$size_features))) return("Молодежная одежда")
    return("Базовый трикотаж")
  }
  
  get_product_types <- function(types) {
    type_names <- c(
      "джинс" = "джинсы",
      "юбк" = "юбки", 
      "плать" = "платья",
      "рубаш" = "рубашки",
      "куртк" = "куртки",
      "пальто" = "пальто",
      "свитер" = "свитеры",
      "брюк" = "брюки",
      "шорт" = "шорты",
      "пиджак" = "пиджаки"
    )
    
    result <- c()
    for(type in types) {
      if(type %in% names(type_names)) {
        result <- c(result, type_names[[type]])
      }
    }
    unique(result)
  }
  
  brand_data <- reactive({
    products_data %>% filter(product_brand == input$selected_brand)
  })
  
  sorted_data <- reactive({
    data <- brand_data()
    
    # основная сортировка
    if(input$sort_primary != "none") {
      if(input$sort_primary_order == "desc") {
        data <- data %>% arrange(desc(.data[[get_sort_column(input$sort_primary)]]))
      } else {
        data <- data %>% arrange(.data[[get_sort_column(input$sort_primary)]])
      }
    }
    
    # Дополнительная сортировка
    if(input$sort_secondary != "none" && input$sort_secondary != input$sort_primary) {
      if(input$sort_secondary_order == "desc") {
        data <- data %>% arrange(desc(.data[[get_sort_column(input$sort_secondary)]]))
      } else {
        data <- data %>% arrange(.data[[get_sort_column(input$sort_secondary)]])
      }
    }
    
    data
  })
  
  get_sort_column <- function(sort_type) {
    switch(sort_type,
           "price" = "price_rub",
           "rating" = "review_rating", 
           "feedbacks" = "feedbacks",
           "quantity" = "total_quantity")
  }
  
  output$products_table <- renderDataTable({
    sorted_data() %>%
      select(product_name, price_rub, review_rating, feedbacks, total_quantity, sizes) %>%
      rename(
        "Товар" = product_name,
        "Цена (руб)" = price_rub,
        "Рейтинг" = review_rating,
        "Отзывы" = feedbacks,
        "На складе" = total_quantity,
        "Размеры" = sizes
      )
  }, options = list(
    pageLength = 10,
    searching = FALSE,
    ordering = FALSE,
    dom = 'tlip'    
  ))
}

shinyApp(ui = ui, server = server)
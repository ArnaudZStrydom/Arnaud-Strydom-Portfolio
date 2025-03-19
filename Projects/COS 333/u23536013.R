# Load the dataset
col_names <- c("Sepal.Length", "Sepal.Width", "Petal.Length", "Petal.Width", "Species")
iris_data <- read.table("iris.data", sep = ",", header = FALSE, col.names = col_names, stringsAsFactors = FALSE) 
# Ensure Petal.Length and Petal.Width are numeric
iris_data$Petal.Length <- as.numeric(iris_data$Petal.Length)
iris_data$Petal.Width <- as.numeric(iris_data$Petal.Width)

# Remove any rows with NA values (just in case)
iris_data <- na.omit(iris_data)

# Plot a scatter plot of Petal Length vs Petal Width
plot(iris_data$Petal.Length, iris_data$Petal.Width, 
     main = "Petal Length vs Petal Width (Iris Dataset)", 
     xlab = "Petal Length", ylab = "Petal Width", 
     col = "blue", pch = 16)

# Fit a third-degree polynomial regression
poly_model <- lm(Petal.Width ~ poly(Petal.Length, 3), data = iris_data)

# Generate polynomial regression curve
x_values <- seq(min(iris_data$Petal.Length), max(iris_data$Petal.Length), length.out = 100)
y_values <- predict(poly_model, newdata = data.frame(Petal.Length = x_values))

# Add polynomial regression curve to scatter plot
lines(x_values, y_values, col = "red", lwd = 2)

# Add grid lines
grid()

# Save the plot as a PNG file
png("scatter_plot.png")
plot(iris_data$Petal.Length, iris_data$Petal.Width, 
     main = "Petal Length vs Petal Width (Iris Dataset)", 
     xlab = "Petal Length", ylab = "Petal Width", 
     col = "blue", pch = 16)
lines(x_values, y_values, col = "red", lwd = 2)
dev.off()  # Close the plotting device

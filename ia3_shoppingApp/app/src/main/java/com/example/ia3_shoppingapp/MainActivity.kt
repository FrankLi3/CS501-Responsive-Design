package com.example.ia3_shoppingapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ia3_shoppingapp.ui.theme.Ia3_shoppingAppTheme


data class Product(val id: Int, val name: String, val description: String, val price: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ia3_shoppingAppTheme {
                val products = listOf(
                    Product(1, "Product 1", "Description of Product 1", 100),
                    Product(2, "Product 2", "Description of Product 2", 200),
                    Product(3, "Product 3", "Description of Product 3", 300),
                    Product(4, "Product 4", "Description of Product 4", 400),
                    Product(5, "Product 5", "Description of Product 5", 500),
                    Product(6, "Product 6", "Description of Product 6", 600),

                )
                ShoppingApp(products)
            }
        }
    }
}

@Composable
fun ProductList(
    // first panel for the list of products
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductSelected: (Product) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(products) { product ->
            Text(
                text = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onProductSelected(product) }
            )
        }
    }
}

@Composable
fun ProductDetails(product: Product?, modifier: Modifier = Modifier, onClearSelection: () -> Unit) {
    Column(
        // second panel for the details of the selected product
        modifier = modifier
            .padding(16.dp)
            .clickable { onClearSelection() }
    ) {
        if (product == null) {
            Text(text = "Select a product to see more information") // default message
        } else {
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = product.description)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: \$${product.price}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Clear",
                modifier = Modifier
                    .clickable{ onClearSelection() }
                    .padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            ) // return button

        }
    }
}

@Composable
fun ShoppingApp(products: List<Product>) {
//    var selectedProduct by rememberSaveable { mutableStateOf<Product?>(null) }
    var selectIndex by rememberSaveable { mutableStateOf(-1) }
//    var portrait by remember { mutableStateOf(true) }
    val config = LocalConfiguration.current // get the current state
    val portrait = config.orientation == Configuration.ORIENTATION_PORTRAIT // check if it is portrait mode

    // portrait mode
    if (portrait) {
        if (selectIndex == -1) {
            ProductList(products) { product ->
                selectIndex = products.indexOf(product)
            }
        } else {
            ProductDetails(product = products[selectIndex]) {
                selectIndex = -1
            }
        }
    } else {
        // landscape mode
        Row {
            ProductList(
                products,
                modifier = Modifier.weight(1f)
            ) { product ->
                selectIndex = products.indexOf(product)
            }
            ProductDetails(
                product = if (selectIndex == -1) null else products[selectIndex],
                modifier = Modifier.weight(1f)
            ) {
                selectIndex = -1
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ia3_shoppingAppTheme {

    }
}
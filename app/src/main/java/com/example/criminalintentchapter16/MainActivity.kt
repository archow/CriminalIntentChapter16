package com.example.criminalintentchapter16

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.criminalintentchapter16.databinding.ActivityMainBinding
import com.example.criminalintentchapter16.ui.theme.CriminalIntentChapter16Theme

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
        //to initialize a fragmentContainer with the NavHostFragment:
        val navHostFragment = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, navHostFragment)
            .setPrimaryNavigationFragment(true)
            .commit()
        */

//        setContent {
//            CriminalIntentChapter16Theme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CriminalIntentChapter16Theme {
        Greeting("Android")
    }
}
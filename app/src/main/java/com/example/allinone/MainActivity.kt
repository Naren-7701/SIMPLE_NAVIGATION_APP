package com.example.allinone

import android.content.Context
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allinone.ui.theme.AllinoneTheme
import java.util.jar.Manifest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllinoneTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val getpermission = rememberLauncherForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted ->
                        if (isGranted) {
                            //permission accepted Do Something
                        } else {
                            // Permission not accepted show message
                        }
                    }
                    SideEffect {
                        getpermission.launch(android.Manifest.permission.SEND_SMS)
                    }
                    App(applicationContext)
                }
            }
        }
    }
}
@Composable
fun App(context: Context)
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomePage(navController = navController)
        }
        composable("next/{name}/{email}/{mobile}/{location}") {
                backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val email = backStackEntry.arguments?.getString("email")
            val phone = backStackEntry.arguments?.getString("mobile")
            val loc = backStackEntry.arguments?.getString("location")
            NextPage(context,name = name, email = email, mobile = phone,location=loc)
        }
        composable("about") {
            AboutPage(context)
        }
        composable("detail") {
            DetailPage(context)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController)
{
    Scaffold(
        content = { h -> HomeContent(h,navController) },
        bottomBar = { HomeBottom(navController)}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(h: PaddingValues,navController: NavController) {

    val name = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val mobile = remember { mutableStateOf(TextFieldValue("")) }
    val location = remember { mutableStateOf(TextFieldValue("")) }
    val checked = remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            label = { Text("Enter Name", fontSize = 13.sp, textAlign = TextAlign.Center) },
            leadingIcon = {Icon(Icons.Outlined.AccountBox, contentDescription = null)}
        )
        TextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            label = { Text("Enter Email ID", fontSize = 13.sp, textAlign = TextAlign.Center) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {Icon(Icons.Outlined.Email, contentDescription = null)}
        )
        TextField(
            value = mobile.value,
            onValueChange = {
                mobile.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            label = {
                Text(
                    "Enter Mobile Number",
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            leadingIcon = {Icon(Icons.Outlined.Phone, contentDescription = null)}
        )
        TextField(
            value = location.value,
            onValueChange = {
                location.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Black)),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
            label = { Text("Enter Location", fontSize = 13.sp, textAlign = TextAlign.Center) },
            leadingIcon = {Icon(Icons.Outlined.LocationOn, contentDescription = null)}
        )
        Row(modifier = Modifier.fillMaxWidth())
        {
            Checkbox(
                checked = checked.value,
                onCheckedChange = { checked.value = it }
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Agree to the Terms and Conditions"
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
        {
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        name.value.text + "\n" + email.value.text + "\n" + mobile.value.text + "\n" + location.value.text,
                        Toast.LENGTH_LONG
                    ).show()
                },
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B6D00)),
                shape = RoundedCornerShape(10),
                enabled = checked.value
            ) {
                Text(text = "Toast Output")
            }
            Button(
                onClick = { visible = !visible },
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B6D00)),
                shape = RoundedCornerShape(10),
                enabled = checked.value
            ) {
                Text(text = "Text Output")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Button(
                onClick = { navController.navigate("next/${name.value.text}/${email.value.text}/${mobile.value.text}/${location.value.text}") },
                modifier = Modifier
                    .width(135.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF0A77AA)),
                shape = RoundedCornerShape(10),
                enabled = checked.value
            ) {
                Text(text = "Next")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            if (visible) {
                Text(
                    text = name.value.text + "\n" + email.value.text + "\n" + mobile.value.text + "\n" + location.value.text,
                    color = Color.Black,
                    fontSize = 15.sp,
                )
            }
        }
    }
}
@Composable
fun HomeBottom(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround)
    {
        Button(
            onClick = {navController.navigate("about")},
            modifier = Modifier.width(90.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE63F22)),
            shape = RoundedCornerShape(10),
        ) {
            Text(text = "About")
        }
        Button(
            onClick = {navController.navigate("detail")},
            modifier = Modifier.width(90.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFE63F22)),
            shape = RoundedCornerShape(10),
        ) {
            Text(text = "Detail")
        }
    }
}
@Composable
fun AboutPage(context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        Image(painter= painterResource(id= R.drawable.naren_logo) , contentDescription = "sample image", modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = "Sample Image My Logo", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(context: Context) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        Text(
            text = "Select your Highest Qualification",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(15.dp))
        var isexpanded by remember { mutableStateOf(false) }
        var qualf by remember { mutableStateOf("") }
        val qualifications = listOf("B.Tech", "BE", "MS", "MBA", "MBBS", "MCA")
        ExposedDropdownMenuBox(expanded = isexpanded,
            onExpandedChange = { isexpanded = it })
        {
            TextField(
                value = qualf,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isexpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(containerColor = Color.White),
                modifier = Modifier
                    .menuAnchor()
                    .border(BorderStroke(2.dp, Color.Black)),
                placeholder = { Text(text = "Select Qualification") },
            )
            ExposedDropdownMenu(
                expanded = isexpanded,
                onDismissRequest = { isexpanded = false },
                modifier = Modifier.background(color = Color.White)
            )
            {
                qualifications.forEach { qualifications ->
                    DropdownMenuItem(
                        text = { Text(text = qualifications) },
                        onClick = {
                            qualf = qualifications
                            isexpanded = false
                        })
                }
            }
        }
        Spacer(modifier = Modifier.padding(15.dp))
        Text(
            text = qualf,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun NextPage(context: Context,name: String?, email: String?, mobile: String?,location: String?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        val openDialog = remember { mutableStateOf(false) }
        LazyColumn(modifier = Modifier.padding(5.dp))
        {
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0A77AA)),
                    shape = RoundedCornerShape(10),
                ) {
                    Text(text = "" + name)
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0A77AA)),
                    shape = RoundedCornerShape(10),
                ) {
                    Text(text = "" + email)
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
            item {
                Button(
                    onClick = { openDialog.value = true },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0A77AA)),
                    shape = RoundedCornerShape(10),
                ) {
                    Text(text = "" + mobile)
                }
                if (openDialog.value) {
                    AlertDialog(
                        onDismissRequest = { openDialog.value = false },
                        title = { Text(text = "Alert") },
                        text = { Text("Are you sure you want to send message to this Number ?") },
                        dismissButton = {
                            Button(
                                onClick = {openDialog.value = false
                                    try {
                                        val smsManager: SmsManager = SmsManager.getDefault()
                                        smsManager.sendTextMessage("+91"+mobile, null, "Hi "+name+" - Allinone App", null, null)
                                        Toast.makeText(context, "Message Sent", Toast.LENGTH_LONG)
                                            .show()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Error: " + e.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(45.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF3B6D00)),
                                shape = RoundedCornerShape(10)
                            )
                            {
                                Text("Yes")
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {openDialog.value =false},
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(45.dp),
                                colors = ButtonDefaults.buttonColors(Color(0xFF3B6D00)),
                                shape = RoundedCornerShape(10)
                            )
                            {
                                Text("Cancel")
                            }
                        })
                }
                Spacer(modifier = Modifier.padding(20.dp))
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF0A77AA)),
                    shape = RoundedCornerShape(10),
                ) {
                    Text(text = "" + location)
                }
            }
        }
    }
}
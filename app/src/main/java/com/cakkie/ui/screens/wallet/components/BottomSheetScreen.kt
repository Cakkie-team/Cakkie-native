//package com.cakkie.ui.screens.wallet.components
//
//import android.widget.Toast
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.BottomSheetScaffold
//import androidx.compose.material.ExperimentalMaterialApi
//import androidx.compose.material.OutlinedTextField
//import androidx.compose.material.Text
//import androidx.compose.material.rememberBottomSheetScaffoldState
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import com.cakkie.R
//import com.cakkie.ui.theme.CakkieBackground
//import com.cakkie.ui.theme.CakkieBrown
//import com.ramcosta.composedestinations.annotation.Destination
//import kotlin.math.sin
//
//@OptIn(ExperimentalMaterialApi::class)
//@Destination
//@Composable
//fun BottomSheetScreen() {
//    val scope = rememberCoroutineScope()
//    val scaffoldState = rememberBottomSheetScaffoldState()
//    var pin by remember {
//        mutableStateOf("")
//    }
//    val context = LocalContext.current
//    val keyboardType  = KeyboardType.Number
//    BottomSheetScaffold(
//        scaffoldState = scaffoldState,
//        sheetPeekHeight = 140.dp,
//        sheetContentColor = Color.White,
//        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
//        sheetContent = {
//            Row {
//
//            }
//        },
//        content = {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(
//                    text = stringResource(id = R.string.enter_transaction_pin),
//                    style = MaterialTheme.typography.bodyMedium
//                )
//                Spacer(modifier = Modifier.height(50.dp))
//
//////                underline style with custom border colors
//                BasicTextField(value = pin,
//                    onValueChange = {
//                        if (it.length <= 4){
////                        if (it..length <= 4) {
//                            pin = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Number,
//                        imeAction = ImeAction.Done
//                    ),
//                    keyboardActions = KeyboardActions(
//                        onDone = {
//
//                        }
//                    ),
//                    visualTransformation = PasswordVisualTransformation(),
//                    textStyle = MaterialTheme.typography.bodyLarge,
//                    singleLine = true,
//                    modifier = Modifier
//                        .underlineTextField()
//                )
////                ComposePinInput(
////                    values = pin,
////                    mask = "*",
////                    cellBorderColor = CakkieBrown,
////                    focuedCellBorderColor = CakkieBrown,
////                    onValueChange = {
////                        pin = it
////                    },
////                    cellSize = 133.dp,
////                    onPinEntered = {
////                        Toast.makeText(
////                            context, "$it", Toast.LENGTH_SHORT
////                        )
////                    },
////                    style = ComposePinInputStyle.Underline
////                )
////                Row(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(start = 40.dp, end = 40.dp),
////                    horizontalArrangement = Arrangement.SpaceBetween
////                ) {
////                    Image(
////                        painter = painterResource(id = R.drawable.line32),
////                        contentDescription = ""
////                    )
////                    Image(
////                        painter = painterResource(id = R.drawable.line32),
////                        contentDescription = "",
////                    )
////                    Image(
////                        painter = painterResource(id = R.drawable.line32),
////                        contentDescription = ""
////                    )
////                    Image(
////                        painter = painterResource(id = R.drawable.line32),
////                        contentDescription = ""
////                    )
////                }
//                Spacer(modifier = Modifier.height(20.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 55.dp, end = 55.dp),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "1",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "2",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                      modifier = Modifier
//                          .width(49.dp)
//                          .height(50.dp)
//                          .background(
//                              color = CakkieBackground,
//                              shape = RoundedCornerShape(size = 3.dp)
//                          ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "3",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 55.dp, end = 55.dp),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "4",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "5",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "6",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 55.dp, end = 55.dp),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "7",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "8",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "9",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 55.dp, end = 55.dp),
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "*",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "0",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Box(
//                        modifier = Modifier
//                            .width(49.dp)
//                            .height(50.dp)
//                            .background(
//                                color = CakkieBackground,
//                                shape = RoundedCornerShape(size = 3.dp)
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "#",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//
//            }
//        }
//    )
//}
////Modifier
////.width(169.dp)
////.height(227.dp)
//@Composable
//fun Modifier.underlineTextField(): Modifier = then(
//    Modifier.border(
//        BorderStroke(2.dp, CakkieBrown),
//        shape = MaterialTheme.shapes.medium
//    )
//)
package com.sample.todo.presentation.screen.support

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sample.todo.R
import com.sample.todo.presentation.component.topbar.TopBarIconButton
import com.sample.todo.presentation.theme.White

@Composable
fun SupportPortraitScreen(
    onBackButtonClick: () -> Unit
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (gradientC, backB, bodyC) = createRefs()
        val oneG = createGuidelineFromTop(0.3F)
        val twoG = createGuidelineFromTop(0.5F)

        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .constrainAs(gradientC) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    linkTo(top = parent.top, bottom = oneG)
                }
        )
        TopBarIconButton(
            onClick = onBackButtonClick,
            icon = Icons.Default.ArrowBack,
            contentDescription = R.string.cd_back_button,
            tint = White,
            modifier = Modifier.constrainAs(backB) {
                start.linkTo(anchor = parent.start, margin = 12.dp)
                top.linkTo(anchor = parent.top, margin = 12.dp)
            }
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.constrainAs(bodyC) {
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
                linkTo(start = parent.start, end = parent.end, startMargin = 30.dp, endMargin = 30.dp)
                linkTo(top = oneG, bottom = twoG)
            }
        ) {

        }

    }
}
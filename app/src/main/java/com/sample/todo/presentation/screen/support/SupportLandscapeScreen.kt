package com.sample.todo.presentation.screen.support

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun SupportLandscapeScreen(
    onBackButtonClick: () -> Unit
) {

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (gradientC) = createRefs()

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(12.dp)
                .constrainAs(gradientC) {
                    width = Dimension.matchParent
                    top.linkTo(parent.top)
                },
        ) {
            TopBarIconButton(
                onClick = onBackButtonClick,
                icon = Icons.Default.ArrowBack,
                contentDescription = R.string.cd_back_button,
                tint = White
            )
            Spacer(
                modifier = Modifier.width(38.dp)
            )
        }

    }
}
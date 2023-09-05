package ru.deltadelete.tictactoe.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.deltadelete.tictactoe.R
import ru.deltadelete.tictactoe.REDMI_NOTE_9_PRO
import ru.deltadelete.tictactoe.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutPage() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(32.dp)
    ) {
        Text(stringResource(R.string.developer), style = Typography.titleLarge)
        Card(Modifier.fillMaxWidth()) {
            Row(
                Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp).clip(CircleShape)
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)) {
                    Text(
                        text = stringResource(R.string.author_nickname),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.author_name),
                        fontSize = 18.sp
                    )
                }
            }
        }
        Card(Modifier.fillMaxWidth()) {
            Row(
                Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {
                val uriHandler = LocalUriHandler.current
                val context = LocalContext.current
                Image(
                    painter = painterResource(R.drawable.vk_logo),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp).clickable { uriHandler.openUri(context.resources.getString(R.string.vk_link)) }
                )
                Image(
                    painter = painterResource(R.drawable.github_mark),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp).clickable { uriHandler.openUri(context.resources.getString(R.string.github_link)) }
                )
            }
        }
    }
}

@Composable
@Preview(device = REDMI_NOTE_9_PRO)
fun Preview() {
    AboutPage()
}


package io.lb.lbtasks.feature_task.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.lb.lbtasks.R
import io.lb.lbtasks.core.presentation.widgets.DefaultIcon

@ExperimentalMaterial3Api
@Composable
fun TaskCard(onClick: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.5F)
        ),
        onClick = { },
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            DefaultIcon(
                modifier = Modifier.size(48.dp),
                iconModifier = Modifier.fillMaxSize(0.7F),
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.background.copy(0.9F),
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "", // Nome da task
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Tarefa registrada",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Text(
                    text = "12/12/2020 12:20",
                    fontSize = 12.sp
                )
            }
        }
    }
}

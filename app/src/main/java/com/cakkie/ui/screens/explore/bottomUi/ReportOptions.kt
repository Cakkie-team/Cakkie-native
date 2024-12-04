import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cakkie.ui.theme.CakkieBackground
import com.cakkie.ui.theme.CakkieBrown
import com.cakkie.ui.theme.Inactive

@Composable
fun ReportOptions(
    onDismiss: () -> Unit,
    onCategorySelected: (String) -> Unit
) {

    val categories = listOf(
        "I just don't like it",
        "Promotes unhealthy eating habits",
        "Violence or offensive language",
        "Selling prohibited or restricted items",
        "Scam, misleading promotions or harmful information",
        "Copyright infringement",
        "Spam or inappropriate content",
        "Unauthorized use of brand or logo",
        "Plagiarized recipes or content",
        "Violation of community guidelines"
    )

    AlertDialog(
        containerColor = CakkieBackground,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Report",
                style = MaterialTheme.typography.headlineMedium,
                color = CakkieBrown
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Why are you reporting this post?",
                    style = MaterialTheme.typography.labelSmall,
                    color = Inactive
                )
                Spacer(modifier = Modifier.height(16.dp))

                categories.forEach { category ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                onCategorySelected(category)
                                onDismiss()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 16.sp,
                            color = CakkieBrown
                        )
                    }
                    Divider(
                        Modifier
                            .clip(RoundedCornerShape(50))
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        color = Inactive,
                        thickness = 1.dp,
                    )
                }
            }
        },
        confirmButton = { /*TODO*/ },
        dismissButton = {
            Text(
                text = "Cancel",
                color = CakkieBrown,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    onDismiss()
                }
            )
        },
    )

}
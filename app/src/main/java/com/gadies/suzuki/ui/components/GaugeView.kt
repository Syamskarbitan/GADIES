package com.gadies.suzuki.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GaugeView(
    value: Double,
    minValue: Double,
    maxValue: Double,
    unit: String,
    title: String,
    size: Int = 200,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(size.dp)
        ) {
            CircularGauge(
                value = value,
                minValue = minValue,
                maxValue = maxValue,
                size = size
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = String.format("%.1f", value),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun CircularGauge(
    value: Double,
    minValue: Double,
    maxValue: Double,
    size: Int
) {
    val surfaceVariantColor = MaterialTheme.colorScheme.surfaceVariant
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant

    Canvas(modifier = Modifier.size(size.dp)) {
        val strokeWidth = size / 20f
        val radius = (size.toFloat() - strokeWidth) / 2
        val center = Offset(size / 2f, size / 2f)
        
        // Draw background arc
        drawArc(
            color = surfaceVariantColor,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
        )
        
        // Draw value arc
        val sweepAngle = ((value - minValue) / (maxValue - minValue) * 270.0).coerceIn(0.0, 270.0)
        drawArc(
            color = primaryColor,
            startAngle = 135f,
            sweepAngle = sweepAngle.toFloat(),
            useCenter = false,
            style = Stroke(strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
        )
        
        // Draw ticks
        for (i in 0..10) {
            val angle = 135 + (270 * i / 10.0)
            val angleRad = angle * PI / 180
            val tickLength = if (i % 5 == 0) strokeWidth else strokeWidth / 2
            val startRadius = radius - tickLength
            val endRadius = radius
            
            val startX = center.x + cos(angleRad).toFloat() * startRadius
            val startY = center.y + sin(angleRad).toFloat() * startRadius
            val endX = center.x + cos(angleRad).toFloat() * endRadius
            val endY = center.y + sin(angleRad).toFloat() * endRadius
            
            drawLine(
                color = onSurfaceVariantColor,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth / 4
            )
        }
    }
}
